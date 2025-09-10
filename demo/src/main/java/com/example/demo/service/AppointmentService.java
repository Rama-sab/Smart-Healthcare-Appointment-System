package com.example.demo.service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.entity.AppointmentStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

  private final AppointmentRepository repo;

  public AppointmentService(AppointmentRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public Appointment markCompleted(Long appointmentId, String currentUsername) {
    Appointment appt = repo.findById(appointmentId)
        .orElseThrow(() -> new NotFoundException("Appointment not found: " + appointmentId));

    Doctor doctor = appt.getDoctor();
    if (doctor == null || doctor.getUser() == null) {
      throw new AccessDeniedException("Appointment has no assigned doctor.");
    }

    User doctorUser = doctor.getUser();
    if (!doctorUser.getUsername().equals(currentUsername)) {
      throw new AccessDeniedException("You can only complete your own appointments.");
    }

    if (appt.getStatus() != AppointmentStatus.COMPLETED) {
      appt.setStatus(AppointmentStatus.COMPLETED);
      appt = repo.save(appt);
    }

    return appt;
  }
}
