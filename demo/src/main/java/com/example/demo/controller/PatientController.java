package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.models.dto.PatientDTO;
import com.example.demo.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/patients")
public class PatientController {
  private final PatientRepository repo;
  public PatientController(PatientRepository repo){ this.repo = repo; }

  @GetMapping public List<Patient> all(){ return repo.findAll(); }

  @PutMapping("/{id}")
  public Patient update(@PathVariable Long id, @RequestBody @Valid PatientDTO dto) {
    return repo.findById(id).map(p -> {
      p.setName(dto.name()); p.setPhone(dto.phone());
      return repo.save(p);
    }).orElseThrow();
  }
}
