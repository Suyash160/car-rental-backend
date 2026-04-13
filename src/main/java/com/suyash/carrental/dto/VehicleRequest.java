package com.suyash.carrental.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VehicleRequest {
    @NotBlank private String brand;
    @NotBlank private String model;
    @NotBlank private String registrationNumber;
    @NotBlank private String category;
    @NotNull @Positive private Double pricePerDay;
    private String imageUrl;
}
