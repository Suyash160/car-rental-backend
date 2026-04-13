package com.suyash.carrental.serviceimpl;

import com.suyash.carrental.dto.VehicleRequest;
import com.suyash.carrental.dto.VehicleResponse;
import com.suyash.carrental.exception.ResourceNotFoundException;
import com.suyash.carrental.model.Vehicle;
import com.suyash.carrental.repository.VehicleRepository;
import com.suyash.carrental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleResponse addVehicle(VehicleRequest request) {
        if (vehicleRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new IllegalArgumentException("Vehicle with registration number already exists: "
                    + request.getRegistrationNumber());
        }
        Vehicle vehicle = Vehicle.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .registrationNumber(request.getRegistrationNumber())
                .category(request.getCategory().toUpperCase())
                .pricePerDay(request.getPricePerDay())
                .available(true)
                .imageUrl(request.getImageUrl())
                .build();
        return mapToResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
        Vehicle vehicle = getVehicleOrThrow(id);
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setCategory(request.getCategory().toUpperCase());
        vehicle.setPricePerDay(request.getPricePerDay());
        vehicle.setImageUrl(request.getImageUrl());
        return mapToResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public void deleteVehicle(Long id) {
        getVehicleOrThrow(id);
        vehicleRepository.deleteById(id);
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {
        return mapToResponse(getVehicleOrThrow(id));
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponse> getAvailableVehicles() {
        return vehicleRepository.findByAvailableTrue().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<VehicleResponse> getAvailableVehiclesByCategory(String category) {
        return vehicleRepository.findByCategoryAndAvailableTrue(category.toUpperCase()).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    private Vehicle getVehicleOrThrow(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    private VehicleResponse mapToResponse(Vehicle v) {
        return VehicleResponse.builder()
                .id(v.getId())
                .brand(v.getBrand())
                .model(v.getModel())
                .registrationNumber(v.getRegistrationNumber())
                .category(v.getCategory())
                .pricePerDay(v.getPricePerDay())
                .available(v.getAvailable())
                .imageUrl(v.getImageUrl())
                .build();
    }
}
