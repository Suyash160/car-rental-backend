package com.suyash.carrental.controller;

import com.suyash.carrental.dto.ApiResponse;
import com.suyash.carrental.dto.VehicleRequest;
import com.suyash.carrental.dto.VehicleResponse;
import com.suyash.carrental.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    // Public endpoints
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getAvailableVehicles() {
        return ResponseEntity.ok(ApiResponse.success("Available vehicles", vehicleService.getAvailableVehicles()));
    }

    @GetMapping("/available/category/{category}")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(ApiResponse.success("Vehicles by category",
                vehicleService.getAvailableVehiclesByCategory(category)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Vehicle details", vehicleService.getVehicleById(id)));
    }

    // Admin-only endpoints
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<VehicleResponse>> addVehicle(@Valid @RequestBody VehicleRequest request) {
        VehicleResponse response = vehicleService.addVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Vehicle added successfully", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateVehicle(@PathVariable Long id,
                                                                       @Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Vehicle updated", vehicleService.updateVehicle(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(ApiResponse.success("Vehicle deleted", null));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getAllVehicles() {
        return ResponseEntity.ok(ApiResponse.success("All vehicles", vehicleService.getAllVehicles()));
    }
}
