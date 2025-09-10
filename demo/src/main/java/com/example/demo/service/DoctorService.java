package com.example.demo.service;

import com.example.demo.entity.Doctor;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.DoctorRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

  private final DoctorRepository repo;

  public DoctorService(DoctorRepository repo) {
    this.repo = repo;
  }


  @Cacheable("doctors")
  public List<Doctor> getAll() {
    return repo.findAll();
  }


  @Cacheable(value = "doctorById", key = "#id")
  public Doctor getById(Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Doctor not found: " + id));
  }

  
  @Transactional
  @Caching(evict = {
      @CacheEvict(cacheNames = "doctors", allEntries = true)
  })
  public Doctor create(Doctor doctor) {
    return repo.save(doctor);
  }

  
  @Transactional
  @Caching(evict = {
      @CacheEvict(cacheNames = "doctors", allEntries = true),
      @CacheEvict(cacheNames = "doctorById", key = "#id")
  })
  public Doctor update(Long id, Doctor changes) {
    Doctor d = getById(id);

  
    if (changes.getName() != null) d.setName(changes.getName());
    if (changes.getSpecialty() != null) d.setSpecialty(changes.getSpecialty());

 
    if (changes.getUser() != null) {
      User u = d.getUser();
      User cu = changes.getUser();

      if (cu.getUsername() != null) u.setUsername(cu.getUsername());
      if (cu.getEmail() != null) u.setEmail(cu.getEmail());
      if (cu.getPassword() != null) u.setPassword(cu.getPassword());
      if (cu.getRole() != null) u.setRole(cu.getRole());
   
      u.setEnabled(cu.isEnabled());
    }

    return repo.save(d);
  }

 
  @Transactional
  @Caching(evict = {
      @CacheEvict(cacheNames = "doctors", allEntries = true),
      @CacheEvict(cacheNames = "doctorById", key = "#id")
  })
  public void delete(Long id) {
    if (!repo.existsById(id)) throw new NotFoundException("Doctor not found: " + id);
    repo.deleteById(id);
  }


  @Cacheable(value = "doctorsBySpecialty", key = "#specialty.toLowerCase()")
  public List<Doctor> findBySpecialty(String specialty) {
    return repo.findBySpecialtyIgnoreCase(specialty);
  }
}
