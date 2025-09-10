package com.example.demo.doctor;

import com.example.demo.controller.DoctorController;
import com.example.demo.entity.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class DoctorControllerTest {

    @Autowired MockMvc mvc;

    @MockBean DoctorRepository doctorRepository;

    @Test
    void listDoctors_ok() throws Exception {
        Doctor d1 = new Doctor();
        d1.setName("Dr. A");
        d1.setSpecialty("Cardiology");

        Doctor d2 = new Doctor();
        d2.setName("Dr. B");
        d2.setSpecialty("Dermatology");

        when(doctorRepository.findAll()).thenReturn(List.of(d1, d2));

        mvc.perform(get("/api/doctors"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].name").value("Dr. A"))
           .andExpect(jsonPath("$[1].specialty").value("Dermatology"));
    }

    @Test
    void createDoctor_validationError_returns400() throws Exception {
        mvc.perform(post("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
           .andExpect(status().isBadRequest());
    }
}
