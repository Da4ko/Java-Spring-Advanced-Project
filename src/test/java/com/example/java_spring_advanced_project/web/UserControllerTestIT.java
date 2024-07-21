package com.example.java_spring_advanced_project.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testRegisterGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userRegisterBindingModel"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testRegisterPostSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("username", "testUser")
                        .param("password", "password")
                        .param("confirmPassword", "password")
                        .param("email", "testUser@example.com")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // Add CSRF token here
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("login"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testRegisterPostValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("username", "testUser")
                        .param("password", "password")
                        .param("confirmPassword", "differentPassword")
                        .param("email", "testUser@example.com")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // Add CSRF token here
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("userRegisterBindingModel"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.userRegisterBindingModel"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testLoginGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // Add CSRF token here
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

}
