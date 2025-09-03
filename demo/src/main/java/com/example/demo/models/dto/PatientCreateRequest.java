package com.example.demo.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PatientCreateRequest(
    @Valid UserCreateDto user,
    @NotBlank String name,
    String phone
) {}

