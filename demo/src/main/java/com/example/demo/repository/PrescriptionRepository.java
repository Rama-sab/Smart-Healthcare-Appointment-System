package com.example.demo.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Prescription;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
     List<Prescription> findByPatientId(Long patientId);
  List<Prescription> findByDoctorId(Long doctorId);
}
