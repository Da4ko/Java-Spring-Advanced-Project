package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBmwCarsDto;
import com.example.java_spring_advanced_project.model.entity.enums.*;
import com.example.java_spring_advanced_project.service.BmwService;
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
public class BmwControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BmwService bmwService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testBmwHome() throws Exception {
        HomeBmwCarsDto homeBmwCars = new HomeBmwCarsDto();

        when(bmwService.getBmwCarsForHomePage()).thenReturn(homeBmwCars);

        mockMvc.perform(MockMvcRequestBuilders.get("/bmw/bmw-cars-home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("BMW-cars"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("homeBmwCarsDto"));
    }

    @Test
    public void testAddBmwPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bmw/add-bmw"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-bmw"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bmwAddBindingModel"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testCreateBmwWithValidData() throws Exception {
        // Prepare the valid BmwAddBindingModel data
        String validBmwModel = BmwModelsEnum.series3.name();
        String validCategoryName = CategoryName.SUV.name();
        String validEngineType = EngineTypeEnum.Diesel.name(); // Use consistent case
        String validTransmissionType = TransmissionType.Manual.name(); // Use consistent case
        String validCurrencyName = CurrencyName.Dollar.name(); // Use consistent case

        // Mock the service to return true for valid data
        when(bmwService.createBmw(any(BmwAddBindingModel.class))).thenReturn(true);

        // Perform the POST request with valid data
        mockMvc.perform(MockMvcRequestBuilders.post("/bmw/add-bmw")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bmwModel", validBmwModel) // Enum to String
                        .param("horsePower", "150")
                        .param("imageUrl", "http://example.com/image.jpg")
                        .param("releaseDate", LocalDate.now().toString())
                        .param("categoryName", validCategoryName) // Enum to String
                        .param("engineType", validEngineType) // Enum to String
                        .param("transmission", validTransmissionType) // Enum to String
                        .param("kilometers", "5000")
                        .param("currencyName", validCurrencyName) // Enum to String
                        .param("price", "25000.00")
                        .param("description", "A great BMW with excellent features.")
                        .with(csrf())) // Include CSRF token
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Expect redirection on success
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bmw/bmw-cars-home")); // Check redirect URL
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testCreateBmwWithInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bmw/add-bmw")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bmwModel", "") // Invalid value
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
                .andExpect(MockMvcResultMatchers.view().name("add-bmw")) ;// Check view name
  }

    @Test
    public void testDeleteBmw() throws Exception {
        String uuid = "some-uuid";

        mockMvc.perform(MockMvcRequestBuilders.delete("/bmw/" + uuid))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bmw/bmw-cars-home"));
    }
}
