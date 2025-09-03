package com.example.demo.models.dto;

public record DoctorResponse(
    Long id,
    String name,
    String specialty,
    Long userId,
    String username,
    String email
) {}
