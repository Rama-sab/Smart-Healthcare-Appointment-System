package com.example.demo.appointment;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.AppointmentStatus;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AppointmentRepositoryTest {

    @Autowired DoctorRepository doctorRepo;
    @Autowired PatientRepository patientRepo;
    @Autowired AppointmentRepository appointmentRepo;

    @Test
    void findByDoctorAndStartTime_returnsExistingAppointment() {
        
        Doctor d = new Doctor();
        d.setName("Dr. Test");
        d.setSpecialty("Dermatology");
        d = doctorRepo.save(d);

     
        Patient p = new Patient();
        p.setName("Alice");
        p = patientRepo.save(p);

        LocalDateTime ldt = LocalDateTime.now()
                .plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
        Instant t = ldt.toInstant(ZoneOffset.UTC);

      
        Appointment a = new Appointment();
        a.setDoctor(d);
        a.setPatient(p);
        a.setStartTime(t);
        a.setStatus(AppointmentStatus.SCHEDULED); 
        appointmentRepo.save(a);

        Optional<Appointment> found = appointmentRepo.findByDoctorAndStartTime(d, t);
        assertThat(found).isPresent();
        assertThat(found.get().getPatient().getName()).isEqualTo("Alice");
    }
}
