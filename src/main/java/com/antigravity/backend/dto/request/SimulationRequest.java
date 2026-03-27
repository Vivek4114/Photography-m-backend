package com.antigravity.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimulationRequest {
    @NotBlank(message = "Simulation name is required")
    private String name;

    private String description;

    @NotNull(message = "User ID is required")
    private Long userId;
}
