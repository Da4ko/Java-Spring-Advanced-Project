package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.MercedesAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeMercedesCarsDto;
import com.example.java_spring_advanced_project.model.entity.enums.*;
import com.example.java_spring_advanced_project.service.MercedesService;
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
public class MercedesControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MercedesService mercedesService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testMercedesHome() throws Exception {
        HomeMercedesCarsDto homeMercedesCars = new HomeMercedesCarsDto();

        when(mercedesService.getMercedesCarsForHomePage()).thenReturn(homeMercedesCars);

        mockMvc.perform(MockMvcRequestBuilders.get("/mercedes/mercedes-cars-home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("mercedes.cars"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("homeMercedesCarsDto"));
    }

    @Test
    public void testAddMercedesPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mercedes/add-mercedes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-mercedes"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("mercedesAddBindingModel"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testCreateMercedesWithValidData() throws Exception {
        // Prepare the valid MercedesAddBindingModel data
        String validMercedesModel = MercedesModelsEnum.C_class.name();
        String validCategoryName = CategoryName.SUV.name();
        String validEngineType = EngineTypeEnum.Diesel.name(); // Use consistent case
        String validTransmissionType = TransmissionType.Automatic.name(); // Use consistent case
        String validCurrencyName = CurrencyName.Euro.name(); // Use consistent case

        // Mock the service to return true for valid data
        when(mercedesService.createMercedes(any(MercedesAddBindingModel.class))).thenReturn(true);

        // Perform the POST request with valid data
        mockMvc.perform(MockMvcRequestBuilders.post("/mercedes/add-mercedes")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("mercedesModel", validMercedesModel) // Enum to String
                        .param("horsePower", "150")
                        .param("imageUrl", "http://example.com/image.jpg")
                        .param("releaseDate", LocalDate.now().toString())
                        .param("categoryName", validCategoryName) // Enum to String
                        .param("engineType", validEngineType) // Enum to String
                        .param("transmission", validTransmissionType) // Enum to String
                        .param("kilometers", "5000")
                        .param("currencyName", validCurrencyName) // Enum to String
                        .param("price", "25000.00")
                        .param("description", "A great car with excellent features.")
                        .with(csrf())) // Include CSRF token
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Expect redirection on success
                .andExpect(MockMvcResultMatchers.redirectedUrl("/mercedes/mercedes-cars-home")); // Check redirect URL
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testCreateMercedesWithInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mercedes/add-mercedes")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("mercedesModel", "") // Invalid value
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
                .andExpect(MockMvcResultMatchers.view().name("add-mercedes")); // Check view name
    }

    @Test
    public void testDeleteMercedes() throws Exception {
        String uuid = "some-uuid";

        mockMvc.perform(MockMvcRequestBuilders.delete("/mercedes/" + uuid))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/mercedes/mercedes-cars-home"));
    }
}
