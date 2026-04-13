package com.suyash.carrental.serviceimpl;

import com.suyash.carrental.dto.BookingRequest;
import com.suyash.carrental.dto.BookingResponse;
import com.suyash.carrental.exception.ResourceNotFoundException;
import com.suyash.carrental.exception.VehicleNotAvailableException;
import com.suyash.carrental.model.Booking;
import com.suyash.carrental.model.User;
import com.suyash.carrental.model.Vehicle;
import com.suyash.carrental.repository.BookingRepository;
import com.suyash.carrental.repository.UserRepository;
import com.suyash.carrental.repository.VehicleRepository;
import com.suyash.carrental.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request, String userEmail) {
        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + request.getVehicleId()));

        if (!vehicle.getAvailable()) {
            throw new VehicleNotAvailableException("Vehicle is not available for booking");
        }

        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                vehicle.getId(), request.getStartDate(), request.getEndDate());
        if (!conflicts.isEmpty()) {
            throw new VehicleNotAvailableException("Vehicle is already booked for the selected dates");
        }

        long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        double totalAmount = days * vehicle.getPricePerDay();

        Booking booking = Booking.builder()
                .user(user)
                .vehicle(vehicle)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .totalAmount(totalAmount)
                .status("CONFIRMED")
                .build();

        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId, String userEmail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + bookingId));

        if (!booking.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("You are not authorized to cancel this booking");
        }

        if (booking.getStatus().equals("CANCELLED")) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }

        booking.setStatus("CANCELLED");
        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponse> getMyBookings(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));
        return bookingRepository.findByUser(user).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponse completeBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + bookingId));
        booking.setStatus("COMPLETED");
        return mapToResponse(bookingRepository.save(booking));
    }

    private BookingResponse mapToResponse(Booking b) {
        return BookingResponse.builder()
                .id(b.getId())
                .vehicleBrand(b.getVehicle().getBrand())
                .vehicleModel(b.getVehicle().getModel())
                .userEmail(b.getUser().getEmail())
                .startDate(b.getStartDate())
                .endDate(b.getEndDate())
                .totalAmount(b.getTotalAmount())
                .status(b.getStatus())
                .build();
    }
}
