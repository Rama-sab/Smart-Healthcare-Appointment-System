package com.example.demo.controller;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.models.UserCreateRequest;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private final UserRepository users;
  private final DoctorRepository doctors;
  private final PatientRepository patients;
  private final PasswordEncoder encoder;

  public AdminController(
      UserRepository users,
      DoctorRepository doctors,
      PatientRepository patients,
      PasswordEncoder encoder
  ) {
    this.users = users;
    this.doctors = doctors;
    this.patients = patients;
    this.encoder = encoder;
  }

  @PostMapping("/users")
  public Long createUser(@RequestBody @Valid UserCreateRequest req) {
    if (users.existsByUsername(req.username()) || users.existsByEmail(req.email())) {
      // no ConflictException by your choice — use BadRequestException
      throw new BadRequestException("username or email exists");
    }

    User u = new User();
    u.setUsername(req.username());
    u.setEmail(req.email());
    u.setPassword(encoder.encode(req.password()));
    u.setRole(req.role());
    u.setEnabled(true);

    return users.save(u).getId();
  }

  @PostMapping("/doctors/{userId}")
  @Transactional
  public Long attachDoctor(
      @PathVariable Long userId,
      @RequestParam String name,
      @RequestParam String specialty
  ) {
    User u = users.findById(userId)
        .orElseThrow(() -> new NotFoundException("user not found"));

    if (u.getRole() != Role.DOCTOR) {
      throw new BadRequestException("user role must be DOCTOR");
    }

    Doctor d = new Doctor();
    d.setUser(u);
    d.setName(name);
    d.setSpecialty(specialty);

    return doctors.save(d).getId();
  }

  @PostMapping("/patients/{userId}")
  @Transactional
  public Long attachPatient(
      @PathVariable Long userId,
      @RequestParam String name,
      @RequestParam(required = false) String phone
  ) {
    User u = users.findById(userId)
        .orElseThrow(() -> new NotFoundException("user not found"));

    if (u.getRole() != Role.PATIENT) {
      throw new BadRequestException("user role must be PATIENT");
    }

    Patient p = new Patient();
    p.setUser(u);
    p.setName(name);
    p.setPhone(phone);

    return patients.save(p).getId();
  }
}
