package com.example.demo.controller;

import com.example.demo.entity.Doctor;
import com.example.demo.models.dto.DoctorDTO;
import com.example.demo.repository.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/doctors")
public class DoctorController {
  private final DoctorRepository repo;
  public DoctorController(DoctorRepository repo){ this.repo = repo; }

  @GetMapping public List<Doctor> all(){ return repo.findAll(); }

  @GetMapping("/search")
  public List<Doctor> bySpecialty(@RequestParam String specialty) {
    return repo.findBySpecialtyIgnoreCase(specialty);
  }

  @PutMapping("/{id}")
  public Doctor update(@PathVariable Long id, @RequestBody @Valid DoctorDTO dto) {
    return repo.findById(id).map(d -> {
      d.setName(dto.name()); d.setSpecialty(dto.specialty());
      return repo.save(d);
    }).orElseThrow();
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id){ repo.deleteById(id); }
}
