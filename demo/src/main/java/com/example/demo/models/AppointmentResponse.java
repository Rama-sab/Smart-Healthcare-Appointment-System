package com.example.demo.models;

import com.example.demo.entity.AppointmentStatus;
import java.time.Instant;

public record AppointmentResponse(
  Long id, 
  Long doctorId, 
  Long patientId, 
  Instant startTime,
   AppointmentStatus status
) {}
