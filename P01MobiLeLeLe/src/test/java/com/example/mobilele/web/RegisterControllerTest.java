package com.example.mobilele.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-register"));
    }

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(post("/user/register")
                        .param("firstName", "Pesho")
                        .param("lastName", "Petrov")
                        .param("username", "Pesho777")
                        .param("password", "topsecret")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void testRegistrationWithWrongUser() throws Exception {
        mockMvc.perform(post("/user/register")
                        .param("firstName", "P")
                        .param("lastName", "P")
                        .param("username", "P")
                        .param("password", "topsecret")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-register"))
                .andExpect(model().attributeHasFieldErrors("userRegistrationDTO", "firstName", "lastName", "username"));
    }
}