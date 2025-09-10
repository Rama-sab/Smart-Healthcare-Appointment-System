package com.example.demo.controller;

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

  private final DoctorService service;

  public DoctorController(DoctorService service) {
    this.service = service;
  }

  @GetMapping
  public List<Doctor> all() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public Doctor byId(@PathVariable Long id) {
    return service.getById(id);
  }

  @GetMapping("/search")
  public List<Doctor> bySpecialty(@RequestParam String specialty) {
    return service.findBySpecialty(specialty);
  }

  @PostMapping
  public Doctor create(@RequestBody Doctor doctor) {
    return service.create(doctor);
  }

  @PutMapping("/{id}")
  public Doctor update(@PathVariable Long id, @RequestBody Doctor changes) {
    return service.update(id, changes);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
