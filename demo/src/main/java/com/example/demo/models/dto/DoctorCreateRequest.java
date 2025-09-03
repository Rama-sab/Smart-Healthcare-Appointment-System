package com.example.demo.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DoctorCreateRequest(
    @Valid UserCreateDto user,
    @NotBlank String name,
    @NotBlank String specialty
) {}
