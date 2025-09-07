package com.example.demo.models;

import com.example.demo.entity.Role;
import jakarta.validation.constraints.*;

public record UserCreateRequest(
  @NotBlank String username,
  @Email @NotBlank String email,
  @NotBlank String password,
  @NotNull Role role
) {}
