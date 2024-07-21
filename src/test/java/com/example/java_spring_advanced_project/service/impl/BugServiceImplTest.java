package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.ReportABugBindingModel;
import com.example.java_spring_advanced_project.model.dto.Bugs.BugDto;
import com.example.java_spring_advanced_project.model.dto.HomeBugsDto;
import com.example.java_spring_advanced_project.model.entity.Bug;
import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.repository.BugsRepository;
import com.example.java_spring_advanced_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BugsServiceImplTest {

    @Mock
    private BugsRepository bugsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    // Injecting mocks into the BugsServiceImpl instance
    @InjectMocks
    private BugsServiceImpl bugsService;

    // Setting up the security context before each test
    @BeforeEach
    public void setup() {
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    public void testGetBugsForBugsHome() {
        // Creating mock Bug entities
        Bug bug1 = new Bug();
        bug1.setId("uuid1");
        bug1.setDescription("Description 1");
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        bug1.setReportedBy(user1);

        Bug bug2 = new Bug();
        bug2.setId("uuid2");
        bug2.setDescription("Description 2");
        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        bug2.setReportedBy(user2);

        // Mocking the repository call to return the created bugs
        when(bugsRepository.findAll()).thenReturn(Arrays.asList(bug1, bug2));

        // Calling the service method
        HomeBugsDto homeBugsDto = bugsService.getBugsForBugsHome();

        // Asserting that the returned DTO list contains the expected data
        List<BugDto> bugDtoList = homeBugsDto.getBugsToBeVisualised();
        assertEquals(2, bugDtoList.size());
        assertEquals("Description 1", bugDtoList.get(0).getDescription());
        assertEquals("Description 2", bugDtoList.get(1).getDescription());
    }

    // Test method for create() when the bug is created successfully
    @Test
    public void testCreateBugSuccessfully() {
        // Creating a binding model with bug details
        ReportABugBindingModel reportABugBindingModel = new ReportABugBindingModel();
        reportABugBindingModel.setDescription("New bug description");

        // Creating a mock UserEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        // Mocking security context and repository calls
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
        when(bugsRepository.save(any(Bug.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Calling the service method
        boolean result = bugsService.create(reportABugBindingModel);

        // Asserting the result and verifying interactions with the repository
        assertTrue(result);
        verify(bugsRepository, times(1)).save(any(Bug.class));
    }


    @Test
    public void testCreateBugUserNotFound() {
        // Creating a binding model with bug details
        ReportABugBindingModel reportABugBindingModel = new ReportABugBindingModel();
        reportABugBindingModel.setDescription("New bug description");

        // Mocking security context and repository calls
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("nonExistentUser");
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Asserting that the service method throws an exception
        assertThrows(UsernameNotFoundException.class, () -> bugsService.create(reportABugBindingModel));
    }


    @Test
    public void testDeleteBug() {
        String bugUuid = "uuid1";

        // Mocking the repository call to delete the bug
        doNothing().when(bugsRepository).deleteById(bugUuid);

        // Calling the service method
        bugsService.deleteBug(bugUuid);

        // Verifying that the repository delete method was called once
        verify(bugsRepository, times(1)).deleteById(bugUuid);
    }
}
