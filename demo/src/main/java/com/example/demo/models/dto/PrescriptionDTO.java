package com.example.demo.models.dto;

import java.util.List;
import jakarta.validation.constraints.*;

public record PrescriptionDTO(
  String id,
  @NotNull Long appointmentId,
  @NotNull Long patientId,
  @NotNull Long doctorId,
  @Size(max=5000) String notes,
  List<MedicineDTO> medicines
) {
  public record MedicineDTO(@NotBlank String name, @NotBlank String dose) {}
}
