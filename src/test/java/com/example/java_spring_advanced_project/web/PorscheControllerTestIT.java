package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.PorscheAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomePorscheCarsDto;
import com.example.java_spring_advanced_project.model.entity.enums.*;
import com.example.java_spring_advanced_project.service.PorscheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class PorscheControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PorscheService porscheService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testPorscheHome() throws Exception {
        HomePorscheCarsDto homePorscheCars = new HomePorscheCarsDto();

        when(porscheService.getPorscheCarsForHomePage()).thenReturn(homePorscheCars);

        mockMvc.perform(MockMvcRequestBuilders.get("/porsche/porsche-cars-home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("porsche-cars"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("homePorscheCarsDto"));
    }

    @Test
    public void testAddPorschePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/porsche/add-porsche"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-porsche"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("porscheAddBindingModel"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testCreatePorscheWithValidData() throws Exception {
        // Prepare the valid PorscheAddBindingModel data
        String validPorscheModel = PorscheModelsEnum.Cayenne.name();
        String validCategoryName = CategoryName.SUV.name();
        String validEngineType = EngineTypeEnum.Diesel.name(); // Use consistent case
        String validTransmissionType = TransmissionType.Automatic.name(); // Use consistent case
        String validCurrencyName = CurrencyName.Euro.name(); // Use consistent case

        // Mock the service to return true for valid data
        when(porscheService.createPorsche(any(PorscheAddBindingModel.class))).thenReturn(true);

        // Perform the POST request with valid data
        mockMvc.perform(MockMvcRequestBuilders.post("/porsche/add-porsche")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("porscheModel", validPorscheModel) // Enum to String
                        .param("horsePower", "450")
                        .param("imageUrl", "http://example.com/image.jpg")
                        .param("releaseDate", LocalDate.now().toString())
                        .param("categoryName", validCategoryName) // Enum to String
                        .param("engineType", validEngineType) // Enum to String
                        .param("transmission", validTransmissionType) // Enum to String
                        .param("kilometers", "5000")
                        .param("currencyName", validCurrencyName) // Enum to String
                        .param("price", "75000.00")
                        .param("description", "A high-performance Porsche with luxurious features.")
                        .with(csrf())) // Include CSRF token
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Expect redirection on success
                .andExpect(MockMvcResultMatchers.redirectedUrl("/porsche/porsche-cars-home")); // Check redirect URL
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testCreatePorscheWithInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/porsche/add-porsche")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("porscheModel", "") // Invalid value
                        .param("horsePower", "0") // Invalid value
                        .param("imageUrl", "") // Invalid value
                        .param("releaseDate", "2025-01-01") // Future date, assuming it's invalid
                        .param("categoryName", "") // Invalid value
                        .param("engineType", "") // Invalid value
                        .param("transmission", "") // Invalid value
                        .param("kilometers", "0") // Invalid value
                        .param("currencyName", "") // Invalid value
                        .param("price", "-1000") // Invalid value
                        .param("description", "") // Invalid value
                        .with(csrf())) // Include CSRF token
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect successful view rendering
                .andExpect(MockMvcResultMatchers.view().name("add-porsche")); // Check view name
    }

    @Test
    public void testDeletePorsche() throws Exception {
        String uuid = "some-uuid";

        mockMvc.perform(MockMvcRequestBuilders.delete("/porsche/" + uuid))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/porsche/porsche-cars-home"));
    }
}
