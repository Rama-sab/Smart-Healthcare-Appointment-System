package com.example.demo.models.dto ;
public record PatientResponse(
    Long id,
    String name,
    String phone,
    Long userId,
    String username,
    String email
) {}
