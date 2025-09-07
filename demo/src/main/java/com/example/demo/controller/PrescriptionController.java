package com.example.demo.controller;

import com.example.demo.entity.Prescription;
import com.example.demo.models.dto.PrescriptionDTO;
import com.example.demo.repository.PrescriptionRepository;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

@RestController @RequestMapping("/api/prescriptions")
public class PrescriptionController {
  private final PrescriptionRepository repo;
  public PrescriptionController(PrescriptionRepository repo){ this.repo = repo; }

  @PostMapping
  public Prescription create(@RequestBody PrescriptionDTO dto){
    Prescription p = new Prescription();
    p.setAppointmentId(dto.appointmentId());
    p.setPatientId(dto.patientId());
    p.setDoctorId(dto.doctorId());
    p.setNotes(dto.notes());
    p.setIssuedAt(Instant.now());

    if(dto.medicines()!=null){
      var meds = dto.medicines().stream().map(m -> {
        Prescription.Medicine mm = new Prescription.Medicine();
        mm.name = m.name();
         mm.dose = m.dose(); 
        return mm;
      }).toList();
      p.setMedicines(meds);
    }
    return repo.save(p);
  }

  @GetMapping("/patient/{patientId}")
  public List<Prescription> byPatient(@PathVariable Long patientId){
    return repo.findByPatientId(patientId);
  }

  @GetMapping("/doctor/{doctorId}")
  public List<Prescription> byDoctor(@PathVariable Long doctorId){
    return repo.findByDoctorId(doctorId);
  }
}
