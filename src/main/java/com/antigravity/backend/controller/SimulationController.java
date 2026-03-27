package com.antigravity.backend.controller;

import com.antigravity.backend.dto.request.SimulationRequest;
import com.antigravity.backend.dto.response.SimulationResponse;
import com.antigravity.backend.service.SimulationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulations")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulationService simulationService;

    @PostMapping("/create")
    public ResponseEntity<SimulationResponse> createSimulation(@Valid @RequestBody SimulationRequest request) {
    	System.out.println("Hello");
        return new ResponseEntity<>(simulationService.createSimulation(request), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SimulationResponse>> getAllSimulations() {
        return ResponseEntity.ok(simulationService.getAllSimulations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationResponse> getSimulationById(@PathVariable Long id) {
        return ResponseEntity.ok(simulationService.getSimulationById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SimulationResponse> updateSimulation(@PathVariable Long id, @Valid @RequestBody SimulationRequest request) {
        return ResponseEntity.ok(simulationService.updateSimulation(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSimulation(@PathVariable Long id) {
        simulationService.deleteSimulation(id);
        return ResponseEntity.noContent().build();
    }
}
