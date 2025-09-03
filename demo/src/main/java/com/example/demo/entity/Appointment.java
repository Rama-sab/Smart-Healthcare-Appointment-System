package com.example.demo.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="appointments",
  uniqueConstraints = @UniqueConstraint(columnNames = {"doctor_id","start_time"})
)
public class Appointment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false) @JoinColumn(name="doctor_id")
  private Doctor doctor;

  @ManyToOne(optional=false) @JoinColumn(name="patient_id")
  private Patient patient;

  @Column(name="start_time", nullable=false) private Instant startTime; 
 

  @Enumerated(EnumType.STRING) @Column(nullable=false)
  private AppointmentStatus status = AppointmentStatus.SCHEDULED;

 
 

  public Appointment() {}

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

 
    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }


  

}
