package com.example.demo.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  Optional<Appointment> findByDoctorAndStartTime(Doctor doctor, Instant startTime);
  List<Appointment> findByDoctorId(Long doctorId);
  List<Appointment> findByPatientId(Long patientId);
}
