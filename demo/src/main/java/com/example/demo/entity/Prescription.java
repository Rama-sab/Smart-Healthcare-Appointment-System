package com.example.demo.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prescriptions")
public class Prescription {
  @Id private String id;
  private Long appointmentId;
  private Long patientId;
  private Long doctorId;

  private Instant issuedAt;
  private String notes;

 public List<Medicine> medicines;

  public Prescription() {}


    public void setId(String id) {
        this.id = id;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public String getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public String getNotes() {
        return notes;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

  public static class Medicine {
    public String name;
    public String dose;
  
    public Integer durationDays;
    public Medicine() {}
  }
 
}
