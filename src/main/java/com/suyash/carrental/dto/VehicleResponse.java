package com.suyash.carrental.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VehicleResponse {
    private Long id;
    private String brand;
    private String model;
    private String registrationNumber;
    private String category;
    private Double pricePerDay;
    private Boolean available;
    private String imageUrl;
}
