package com.example.demo.models.dto;

import jakarta.validation.constraints.*;
public record DoctorDTO(
  Long id,
  @NotBlank String name,
  @NotBlank String specialty,
  Long userId
) {}
