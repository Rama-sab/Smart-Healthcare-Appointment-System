package com.example.demo.security;

import com.example.demo.controller.AdminController;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false) 
class SecurityAccessTest {

    @Autowired MockMvc mvc;

    
    @MockBean UserRepository userRepository;

    @Test
    void adminEndpoint_smoke_okWithoutFilters() throws Exception {
        mvc.perform(get("/api/admin/users"))
           .andExpect(status().isOk());
    }
}
