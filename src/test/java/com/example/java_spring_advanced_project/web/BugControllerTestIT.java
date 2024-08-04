package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.config.TestSecurityConfig;
import com.example.java_spring_advanced_project.model.entity.Bug;
import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.model.entity.enums.RoleEnum;
import com.example.java_spring_advanced_project.repository.BugsRepository;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class) // Import the test security configuration
@Transactional
public class BugControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BugsRepository bugsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private String bugUuid;

    private UserEntity testUser;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        bugsRepository.deleteAll();

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(RoleEnum.user);
        userRoleRepository.save(userRole);

        testUser = new UserEntity();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        testUser.setActive(true);

        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(userRole);
        testUser.setRoles(roles);

        userRepository.save(testUser);

        Bug bug = new Bug();
        bug.setId(UUID.randomUUID().toString());
        bug.setDescription("Test Bug");
        bug.setReportedBy(testUser);
        bugsRepository.save(bug);

        bugUuid = bug.getId();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testReportedBugsHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bugs/reported-bugs"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bugs"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("homeBugsDto"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAddingBug() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bugs/add-report"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-a-bug"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reportABugBindingModel"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testAddingBugConfirmSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bugs/add-report")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "Bug description")
                        .param("reportedBy", "testuser")
                        .with(csrf())) // Include CSRF token
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bugs/thank-you"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAddingBugConfirmFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bugs/add-report")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "")
                        .param("reportedBy", "")
                        .with(csrf())) // Include CSRF token
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("report-a-bug"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testSuccessfulReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bugs/thank-you"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("thank-you-for-the-report"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteBug() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/bugs/{uuid}", bugUuid)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bugs/reported-bugs"));
    }
}
