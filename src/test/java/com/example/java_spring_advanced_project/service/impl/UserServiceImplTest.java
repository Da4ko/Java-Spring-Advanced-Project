package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.ChangeUsernameBindingModel;
import com.example.java_spring_advanced_project.model.binding.UserRegisterBindingModel;
import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import com.example.java_spring_advanced_project.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Initializes mocks and injects them automatically
public class UserServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder; // Mock for password encoding

    @Mock
    private UserRoleRepository userRoleRepository; // Mock for user role repository

    @Mock
    private UserRepository userRepository; // Mock for user repository

    @InjectMocks
    private UserServiceImpl userService; // The service to be tested, with mocked dependencies injected

    @Test
    public void testRegister() {
        // Mocking UserRoleEntity objects
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setId(1L);
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setId(2L);

        // Define behavior for role repository
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(adminRole));
        when(userRoleRepository.findById(2L)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword"); // Mock password encoding

        // Create a UserRegisterBindingModel with sample data
        UserRegisterBindingModel bindingModel = new UserRegisterBindingModel();
        bindingModel.setUsername("adminUser");
        bindingModel.setEmail("user@domain.admin");
        bindingModel.setPassword("password");

        // Call the register method of userService
        userService.register(bindingModel);

        // Verify that userRepository.save was called with a UserEntity having the correct attributes
        verify(userRepository, times(1)).save(argThat(user ->
                "adminUser".equals(user.getUsername()) && // Check username
                        "encodedPassword".equals(user.getPassword()) && // Check encoded password
                        user.getRoles().size() == 2 // Check number of roles (admin and user)
        ));
    }

    @Test
    public void testChangeUsernameSuccess() {
        // Mock Authentication and SecurityContext to simulate a logged-in user
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("currentUser"); // Mock the username of the logged-in user

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails); // Set the principal to be the mocked userDetails

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication); // Set the authentication in the security context

        SecurityContextHolder.setContext(securityContext); // Set the security context for the current thread

        // Mock UserRepository behavior
        UserEntity user = new UserEntity();
        user.setUsername("currentUser"); // Mock a user with the current username
        when(userRepository.findByUsername("currentUser")).thenReturn(Optional.of(user)); // Find the user by username
        when(userRepository.existsByUsername("newUserName")).thenReturn(false); // Simulate that the new username does not exist

        // Create a ChangeUsernameBindingModel with the new username
        ChangeUsernameBindingModel bindingModel = new ChangeUsernameBindingModel();
        bindingModel.setNewUserName("newUserName");

        // Call the changeUsername method of userService
        boolean result = userService.changeUsername(bindingModel);

        // Verify that userRepository.save was called with the updated username
        verify(userRepository).save(argThat(u -> "newUserName".equals(u.getUsername())));
        assert(result); // Assert that the method returned true, indicating success
    }

    @Test
    public void testChangeUsernameFailure() {
        // Mock Authentication and SecurityContext to simulate a logged-in user
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("currentUser"); // Mock the username of the logged-in user

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails); // Set the principal to be the mocked userDetails

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication); // Set the authentication in the security context

        SecurityContextHolder.setContext(securityContext); // Set the security context for the current thread

        // Mock UserRepository behavior
        UserEntity user = new UserEntity();
        user.setUsername("currentUser"); // Mock a user with the current username
        when(userRepository.findByUsername("currentUser")).thenReturn(Optional.of(user)); // Find the user by username
        when(userRepository.existsByUsername("newUserName")).thenReturn(true); // Simulate that the new username already exists

        // Create a ChangeUsernameBindingModel with the new username
        ChangeUsernameBindingModel bindingModel = new ChangeUsernameBindingModel();
        bindingModel.setNewUserName("newUserName");

        // Call the changeUsername method of userService
        boolean result = userService.changeUsername(bindingModel);

        // Verify that userRepository.save was not called because the username change should fail
        verify(userRepository, never()).save(any(UserEntity.class));
        assert(!result); // Assert that the method returned false, indicating failure
    }
}
