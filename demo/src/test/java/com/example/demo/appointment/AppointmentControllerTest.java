package com.example.demo.appointment;

import com.example.demo.controller.AppointmentController;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.AppointmentStatus;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)  
@ActiveProfiles("test")
class AppointmentControllerTest {

    @Autowired MockMvc mvc;

    @MockBean AppointmentRepository appointmentRepository;
    @MockBean DoctorRepository doctorRepository;
    @MockBean PatientRepository patientRepository;

    Doctor d;
    Patient p;
    Instant start;

    @BeforeEach
    void setUp() {
        d = new Doctor();
        d.setName("Dr. Jane");
        d.setSpecialty("Cardiology");

        p = new Patient();
        p.setName("John");

        start = Instant.parse("2030-01-01T10:00:00Z");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(d));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(p));
    }

    @Test
    void bookAppointment_conflict_returns409() throws Exception {
        Appointment existing = new Appointment();
        existing.setId(99L);
        existing.setDoctor(d);
        existing.setPatient(p);
        existing.setStartTime(start);
        existing.setStatus(AppointmentStatus.SCHEDULED); 

        when(appointmentRepository.findByDoctorAndStartTime(eq(d), any()))
                .thenReturn(Optional.of(existing));

        String body = """
        {
          "doctorId": 1,
          "patientId": 1,
          "startTime": "2030-01-01T10:00:00"
        }
        """;

        mvc.perform(post("/api/appointments/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
           .andExpect(status().isConflict())
           .andExpect(jsonPath("$.error").value("DOUBLE_BOOKING"));
    }

    @Test
    void bookAppointment_success_returns201() throws Exception {
        when(appointmentRepository.findByDoctorAndStartTime(eq(d), any()))
                .thenReturn(Optional.empty());
        when(appointmentRepository.save(any())).thenAnswer(inv -> {
            Appointment a = inv.getArgument(0);
            a.setId(123L);
            return a;
        });

        String body = """
        {
          "doctorId": 1,
          "patientId": 1,
          "startTime": "2030-01-01T10:00:00"
        }
        """;

        mvc.perform(post("/api/appointments/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location",
                   org.hamcrest.Matchers.containsString("/api/appointments/123")));
    }
}
