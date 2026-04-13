package com.suyash.carrental.controller;

import com.suyash.carrental.dto.ApiResponse;
import com.suyash.carrental.dto.BookingRequest;
import com.suyash.carrental.dto.BookingResponse;
import com.suyash.carrental.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request, Principal principal) {
        BookingResponse response = bookingService.createBooking(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking confirmed", response));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
            @PathVariable Long id, Principal principal) {
        BookingResponse response = bookingService.cancelBooking(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled", response));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getMyBookings(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Your bookings",
                bookingService.getMyBookings(principal.getName())));
    }

    // Admin endpoints
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        return ResponseEntity.ok(ApiResponse.success("All bookings", bookingService.getAllBookings()));
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookingResponse>> completeBooking(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Booking completed", bookingService.completeBooking(id)));
    }
}
