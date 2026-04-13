package com.suyash.carrental.service;

import com.suyash.carrental.dto.VehicleRequest;
import com.suyash.carrental.dto.VehicleResponse;

import java.util.List;

public interface VehicleService {
    VehicleResponse addVehicle(VehicleRequest request);
    VehicleResponse updateVehicle(Long id, VehicleRequest request);
    void deleteVehicle(Long id);
    VehicleResponse getVehicleById(Long id);
    List<VehicleResponse> getAllVehicles();
    List<VehicleResponse> getAvailableVehicles();
    List<VehicleResponse> getAvailableVehiclesByCategory(String category);
}
