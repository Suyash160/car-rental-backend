package com.suyash.carrental.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private String category; // SEDAN, SUV, HATCHBACK, LUXURY

    @Column(nullable = false)
    private Double pricePerDay;

    @Column(nullable = false)
    private Boolean available;

    private String imageUrl;
}
