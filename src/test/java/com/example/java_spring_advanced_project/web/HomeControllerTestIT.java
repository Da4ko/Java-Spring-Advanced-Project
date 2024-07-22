package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.ChangeUsernameBindingModel;
import com.example.java_spring_advanced_project.service.UserService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // Optionally set up any needed objects or mocks
    }

    @Test
    public void testHomePageWithoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home_not_logged_in"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testHomePageWithUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home_logged_in"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("changeUsernameBindingModel"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testChangeUsernameWithValidData() throws Exception {
        // Prepare the valid ChangeUsernameBindingModel data
        ChangeUsernameBindingModel validModel = new ChangeUsernameBindingModel();
        validModel.setNewUserName("validUsername"); // Ensure this username is valid and not empty

        // Mock the service to return true for valid data
        when(userService.changeUsername(any(ChangeUsernameBindingModel.class))).thenReturn(true);

        // Perform the POST request with valid data
        mockMvc.perform(MockMvcRequestBuilders.post("/home")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("newUserName", validModel.getNewUserName())  // Ensure this matches the field name
                        .with(csrf()))  // Include CSRF token
                .andDo(MockMvcResultHandlers.print())  // Print response details for debugging
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())  // Expect redirection on success
                .andExpect(MockMvcResultMatchers.redirectedUrl("/changed-username"));  // Check redirect URL
    }



    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testChangeUsernameWithInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/home")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("newUsername", "") // Invalid value
                        .with(csrf())) // Include CSRF token
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home_logged_in"));

    }

    @Test
    public void testAboutUsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about-us"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("about-us"));
    }

    @Test
    public void testChangedUsernamePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/changed-username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("changed-username-page"));
    }
}
