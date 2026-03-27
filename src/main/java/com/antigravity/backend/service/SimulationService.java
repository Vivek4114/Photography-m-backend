package com.antigravity.backend.service;

import com.antigravity.backend.dto.request.SimulationRequest;
import com.antigravity.backend.dto.response.SimulationResponse;
import com.antigravity.backend.entity.Simulation;
import com.antigravity.backend.entity.User;
import com.antigravity.backend.exception.ResourceNotFoundException;
import com.antigravity.backend.repository.SimulationRepository;
import com.antigravity.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private final SimulationRepository simulationRepository;
    private final UserRepository userRepository;

    public List<SimulationResponse> getAllSimulations() {
        return simulationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SimulationResponse getSimulationById(Long id) {
        Simulation simulation = simulationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Simulation not found with id: " + id));
        return mapToResponse(simulation);
    }

    @Transactional
    public SimulationResponse createSimulation(SimulationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Simulation simulation = Simulation.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();
        
        Simulation savedSimulation = simulationRepository.save(simulation);
        return mapToResponse(savedSimulation);
    }

    @Transactional
    public SimulationResponse updateSimulation(Long id, SimulationRequest request) {
        Simulation simulation = simulationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Simulation not found with id: " + id));
        
        simulation.setName(request.getName());
        simulation.setDescription(request.getDescription());
        
        if (!simulation.getUser().getId().equals(request.getUserId())) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
            simulation.setUser(user);
        }

        Simulation updatedSimulation = simulationRepository.save(simulation);
        return mapToResponse(updatedSimulation);
    }

    @Transactional
    public void deleteSimulation(Long id) {
        Simulation simulation = simulationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Simulation not found with id: " + id));
        simulationRepository.delete(simulation);
    }

    private SimulationResponse mapToResponse(Simulation simulation) {
        return SimulationResponse.builder()
                .id(simulation.getId())
                .name(simulation.getName())
                .description(simulation.getDescription())
                .userId(simulation.getUser().getId())
                .createdAt(simulation.getCreatedAt())
                .updatedAt(simulation.getUpdatedAt())
                .build();
    }
}
