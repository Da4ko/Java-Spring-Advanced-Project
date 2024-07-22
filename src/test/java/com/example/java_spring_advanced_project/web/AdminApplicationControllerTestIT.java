package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.ApplicationSubmitBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAdminApplicationDto;
import com.example.java_spring_advanced_project.service.AdminApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminApplicationControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminApplicationService adminApplicationService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {

    }

    @Test
    @WithMockUser(username = "testUser")
    public void testViewApplications() throws Exception {
        HomeAdminApplicationDto dto = new HomeAdminApplicationDto();
        // Set up the dto object as needed
        when(adminApplicationService.getApplicationsForBugsHome()).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/apply/view-applications"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin.application"))
                .andExpect(MockMvcResultMatchers.model().attribute("homeAdminApplicationDto", dto));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testApplyForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/apply/make-application"))
                .andExpect(status().isOk())
                .andExpect(view().name("apply.for.admin"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("applicationSubmitBindingModel"));
    }
    //todo
  /*  @Test
    @WithMockUser(username = "testUser")
    public void testApplyConfirmValidationErrors() throws Exception {
        // Create a model with validation errors
        ApplicationSubmitBindingModel applicationSubmitBindingModel = new ApplicationSubmitBindingModel();
        // Assuming description is required or has other validation constraints
        applicationSubmitBindingModel.setDescription("Test description");

        // Simulate validation errors
        when(adminApplicationService.create(any(ApplicationSubmitBindingModel.class))).thenReturn(false);

        // Perform the request with validation errors
        mockMvc.perform(post("/make-application")
                        .param("description", "Test description") // Include parameters as needed
                        .flashAttr("applicationSubmitBindingModel", applicationSubmitBindingModel) // Include model attribute
                        .flashAttr("org.springframework.validation.BindingResult.applicationSubmitBindingModel", mock(BindingResult.class)) // Include mock BindingResult
                        ) // Include CSRF token if CSRF protection is enabled
                .andExpect(status().isOk())
                .andExpect(view().name("apply.for.admin"));
    }*/
    @Test
    @WithMockUser(username = "testUser")
    public void testApplyConfirmSuccess() throws Exception {
        ApplicationSubmitBindingModel bindingModel = new ApplicationSubmitBindingModel();
        bindingModel.setDescription("Test description");

        when(adminApplicationService.create(any(ApplicationSubmitBindingModel.class))).thenReturn(true);

        mockMvc.perform(post("/apply/make-application")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "Test description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/apply/successful-application"));
    }





    @Test
    @WithMockUser(username = "testUser")
    public void testSuccessfulApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/apply/successful-application"))
                .andExpect(status().isOk())
                .andExpect(view().name("your-application-has-been-accepted"));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testDeleteApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/apply/testUuid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/apply/view-applications"));

        verify(adminApplicationService, times(1)).deleteApplication("testUuid");
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testAcceptApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/apply/accept/testUuid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/apply/view-applications"));

        verify(adminApplicationService, times(1)).giveAdmin("testUuid");
    }
}
