package com.example.demo.models;

import jakarta.validation.constraints.*;

public record AppointmentRequest(
  @NotNull Long doctorId,
  @NotNull Long patientId,
  @NotBlank String startTime
) {}
