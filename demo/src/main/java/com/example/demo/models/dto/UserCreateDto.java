package com.example.demo.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
    @NotBlank String username,
    
    @Email String email,
    @NotBlank String password
) {}
