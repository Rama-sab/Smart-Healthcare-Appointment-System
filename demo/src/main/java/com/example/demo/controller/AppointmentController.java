package com.example.demo.controller;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.AppointmentStatus;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.models.AppointmentRequest;
import com.example.demo.models.AppointmentResponse;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

  private final AppointmentRepository appts;
  private final DoctorRepository doctors;
  private final PatientRepository patients;

  public AppointmentController(AppointmentRepository appts,
                               DoctorRepository doctors,
                               PatientRepository patients) {
    this.appts = appts;
    this.doctors = doctors;
    this.patients = patients;
  }

  @PostMapping("/book")
  public AppointmentResponse book(@RequestBody @Valid AppointmentRequest req) {
    Doctor d = doctors.findById(req.doctorId())
        .orElseThrow(() -> new NotFoundException("doctor not found"));
    Patient p = patients.findById(req.patientId())
        .orElseThrow(() -> new NotFoundException("patient not found"));

    Instant start;
    try {
      start = Instant.parse(req.startTime());
    } catch (DateTimeParseException e) {
      throw new BadRequestException("invalid ISO time");
    }

    appts.findByDoctorAndStartTime(d, start)
        .ifPresent(a -> { throw new BadRequestException("time slot already taken"); });

    Appointment a = new Appointment();
    a.setDoctor(d);
    a.setPatient(p);
    a.setStartTime(start);
    a.setStatus(AppointmentStatus.SCHEDULED);

    a = appts.save(a);

    return new AppointmentResponse(
        a.getId(),
        d.getId(),
        p.getId(),
        a.getStartTime(),
        a.getStatus()
    );
  }

  @PostMapping("/{id}/cancel")
  public AppointmentResponse cancel(@PathVariable Long id) {
    Appointment a = appts.findById(id)
        .orElseThrow(() -> new NotFoundException("appointment not found"));
    a.setStatus(AppointmentStatus.CANCELLED);
    a = appts.save(a);

    return new AppointmentResponse(
        a.getId(),
        a.getDoctor().getId(),
        a.getPatient().getId(),
        a.getStartTime(),
        a.getStatus()
    );
  }

  @PostMapping("/{id}/complete")
  public AppointmentResponse complete(@PathVariable Long id) {
    Appointment a = appts.findById(id)
        .orElseThrow(() -> new NotFoundException("appointment not found"));
    a.setStatus(AppointmentStatus.COMPLETED);
    a = appts.save(a);

    return new AppointmentResponse(
        a.getId(),
        a.getDoctor().getId(),
        a.getPatient().getId(),
        a.getStartTime(),
        a.getStatus()
    );
  }

  @GetMapping("/doctor/{doctorId}")
  public List<Appointment> byDoctor(@PathVariable Long doctorId) {
    return appts.findByDoctorId(doctorId);
  }

  @GetMapping("/patient/{patientId}")
  public List<Appointment> byPatient(@PathVariable Long patientId) {
    return appts.findByPatientId(patientId);
  }
}
