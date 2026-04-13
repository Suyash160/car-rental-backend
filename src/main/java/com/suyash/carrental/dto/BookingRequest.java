package com.suyash.carrental.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingRequest {
    @NotNull private Long vehicleId;
    @NotNull private LocalDate startDate;
    @NotNull private LocalDate endDate;
}
