package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.ApplicationSubmitBindingModel;
import com.example.java_spring_advanced_project.model.dto.Applications.ApplicationDto;
import com.example.java_spring_advanced_project.model.dto.HomeAdminApplicationDto;
import com.example.java_spring_advanced_project.model.entity.AdminApplication;
import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.model.entity.enums.RoleEnum;
import com.example.java_spring_advanced_project.repository.AdminApplicationsRepository;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import com.example.java_spring_advanced_project.service.impl.AdminApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminApplicationServiceImplTest {

    @Mock
    private AdminApplicationsRepository adminApplicationsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private AdminApplicationServiceImpl adminApplicationService;

    @Test
    public void testGetApplicationsForBugsHome() {
        List<AdminApplication> applications = new ArrayList<>();

        // Create a mock AdminApplication with a non-null applicant
        AdminApplication adminApplication = new AdminApplication();
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        adminApplication.setApplicant(user);

        applications.add(adminApplication);

        // Mock the repository to return the mock applications
        when(adminApplicationsRepository.findAll()).thenReturn(applications);

        // Call the method to test
        HomeAdminApplicationDto result = adminApplicationService.getApplicationsForBugsHome();

        // Verify the results
        assertNotNull(result);
        assertEquals(1, result.getApplicationsToBeVisualised().size());

        // Verify that the repository method was called once
        verify(adminApplicationsRepository, times(1)).findAll();
    }


    @Test
    @WithMockUser(username = "testUser")
    public void testCreateApplicationSuccess() {
        ApplicationSubmitBindingModel applicationSubmitBindingModel = new ApplicationSubmitBindingModel();
        applicationSubmitBindingModel.setDescription("Test description");

        // Mock UserDetails and SecurityContextHolder
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null));

        // Mock the repository to return a user
        UserEntity user = new UserEntity();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Mock the save method to return a new AdminApplication
        when(adminApplicationsRepository.save(any(AdminApplication.class))).thenReturn(new AdminApplication());

        // Call the method to test
        boolean result = adminApplicationService.create(applicationSubmitBindingModel);

        // Verify the results
        assertTrue(result);

        // Verify that the repository save method was called once
        verify(adminApplicationsRepository, times(1)).save(any(AdminApplication.class));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testCreateApplicationUserNotFound() {
        ApplicationSubmitBindingModel applicationSubmitBindingModel = new ApplicationSubmitBindingModel();
        applicationSubmitBindingModel.setDescription("Test description");

        // Mock UserDetails and SecurityContextHolder
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null));

        // Mock the repository to return an empty Optional
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        // Verify that the method throws a UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> {
            adminApplicationService.create(applicationSubmitBindingModel);
        });

        // Verify that the repository save method was never called
        verify(adminApplicationsRepository, never()).save(any(AdminApplication.class));
    }


    @Test
    public void testDeleteApplication() {
        // Call the method to test
        adminApplicationService.deleteApplication("testUuid");

        // Verify that the repository delete method was called once
        verify(adminApplicationsRepository, times(1)).deleteById("testUuid");
    }


    @Test
    public void testGiveAdminSuccess() {
        String uuid = "testUuid";

        // Create a mock AdminApplication with a non-null applicant
        AdminApplication adminApplication = new AdminApplication();
        UserEntity user = new UserEntity();
        adminApplication.setApplicant(user);

        // Mock the repository to return the mock application
        when(adminApplicationsRepository.findById(uuid)).thenReturn(Optional.of(adminApplication));

        // Create a mock admin role
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setId(1L);
        adminRole.setRole(RoleEnum.admin);

        // Mock the repository to return the admin role
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(adminRole));

        // Call the method to test
        adminApplicationService.giveAdmin(uuid);

        // Verify that the user repository save method was called once
        verify(userRepository, times(1)).save(user);

        // Verify that the admin application repository delete method was called once
        verify(adminApplicationsRepository, times(1)).deleteById(uuid);
    }

    /**
     * Test to verify that the method giveAdmin() throws an exception when the admin application is not found
     */
    @Test
    public void testGiveAdminApplicationNotFound() {
        String uuid = "testUuid";

        // Mock the repository to return an empty Optional
        when(adminApplicationsRepository.findById(uuid)).thenReturn(Optional.empty());

        // Verify that the method throws a RuntimeException
        assertThrows(RuntimeException.class, () -> {
            adminApplicationService.giveAdmin(uuid);
        });

        // Verify that the user repository save method was never called
        verify(userRepository, never()).save(any(UserEntity.class));

        // Verify that the admin application repository delete method was never called
        verify(adminApplicationsRepository, never()).deleteById(uuid);
    }
    @Test
    public void testGiveAdminUserAlreadyHasAdminRole() {
        // Prepare test data
        String uuid = "testUuid";
        AdminApplication adminApplication = new AdminApplication();
        UserEntity user = new UserEntity();

        // Set up user with admin role
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRole(RoleEnum.admin);
        user.getRoles().add(adminRole);

        adminApplication.setApplicant(user);

        // Mock repository interactions
        when(adminApplicationsRepository.findById(uuid)).thenReturn(Optional.of(adminApplication));

        // Call the method under test
        adminApplicationService.giveAdmin(uuid);

        // Verify that the user repository's save method was not called, as no new role should be added
        verify(userRepository, never()).save(user);

        // Verify that the admin application repository's delete method was called
        verify(adminApplicationsRepository, times(1)).deleteById(uuid);
    }

    @Test
    public void testGiveAdminFetchAndAddRole() {
        // Prepare test data
        String uuid = "testUuid";

        // Mock admin application with a user who does not have admin role yet
        UserEntity user = new UserEntity();
        user.setRoles(new ArrayList<>()); // Initialize roles list

        AdminApplication adminApplication = new AdminApplication();
        adminApplication.setApplicant(user);

        // Mock repository responses
        when(adminApplicationsRepository.findById(uuid)).thenReturn(Optional.of(adminApplication));

        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setId(1L);
        adminRole.setRole(RoleEnum.admin);

        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(adminRole));

        // Mock user repository save method
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        // Call the method under test
        adminApplicationService.giveAdmin(uuid);

        // Verify that the admin role was added to the user's roles
        assertTrue(user.getRoles().contains(adminRole));

        // Verify that the user repository's save method was called
        verify(userRepository, times(1)).save(user);

        // Verify that the admin application repository's delete method was called
        verify(adminApplicationsRepository, times(1)).deleteById(uuid);
    }
//TODO eventually add one more test

}
