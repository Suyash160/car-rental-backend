package com.suyash.carrental.repository;

import com.suyash.carrental.model.Booking;
import com.suyash.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByStatus(String status);

    @Query("SELECT b FROM Booking b WHERE b.vehicle.id = :vehicleId " +
           "AND b.status != 'CANCELLED' " +
           "AND (b.startDate <= :endDate AND b.endDate >= :startDate)")
    List<Booking> findConflictingBookings(@Param("vehicleId") Long vehicleId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}
