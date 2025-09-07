package com.example.demo.models.dto;

import jakarta.validation.constraints.*;
public record PatientDTO(
  Long id,
  @NotBlank String name,
  @Pattern(regexp = "^[0-9+\\- ]{6,20}$", message="invalid phone") String phone,
  Long userId
) {}
