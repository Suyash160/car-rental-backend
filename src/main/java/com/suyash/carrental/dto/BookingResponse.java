package com.suyash.carrental.dto;

import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingResponse {
    private Long id;
    private String vehicleBrand;
    private String vehicleModel;
    private String userEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalAmount;
    private String status;
}
