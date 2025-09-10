package com.example.demo.testutil;

import com.example.demo.entity.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class Fixtures {
    private Fixtures() {}

    public static Doctor doctor(Long id) {
        Doctor d = new Doctor();
        d.setId(id);
        d.setName("Dr. Jane Doe");
        d.setSpecialty("Cardiology");
        return d;
    }

    public static Patient patient(Long id) {
        User u = new User();
        u.setId(id);
        u.setUsername("johnpatient");
        u.setEmail("john@example.com");
        u.setPassword("secret");
        u.setRole(Role.PATIENT);

        Patient p = new Patient();
        p.setId(id);
        p.setName("John Patient");
        p.setUser(u);

        return p; 
    }

    public static Appointment appointment(Long id, Doctor d, Patient p, LocalDateTime start) {
        Appointment a = new Appointment();
        a.setId(id);
        a.setDoctor(d);
        a.setPatient(p);

        
        Instant instant = start.toInstant(ZoneOffset.UTC);
        a.setStartTime(instant);

        a.setStatus(AppointmentStatus.SCHEDULED); 
        return a;
    }
}
