package com.suyash.carrental.service;

import com.suyash.carrental.dto.BookingRequest;
import com.suyash.carrental.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String userEmail);
    BookingResponse cancelBooking(Long bookingId, String userEmail);
    List<BookingResponse> getMyBookings(String userEmail);
    List<BookingResponse> getAllBookings();
    BookingResponse completeBooking(Long bookingId);
}
