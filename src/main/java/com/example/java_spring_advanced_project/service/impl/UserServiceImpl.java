package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.UserRegisterBindingModel;
import com.example.java_spring_advanced_project.model.entity.User;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import com.example.java_spring_advanced_project.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserRegisterBindingModel userRegisterBindingModel) {
        User user = new User();

        user.setUsername(userRegisterBindingModel.getUsername());
        user.setEmail(userRegisterBindingModel.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        user.setListOfAudiCars(new ArrayList<>());
        user.setListOfBmwCars(new ArrayList<>());
        user.setListOfMercedesCars(new ArrayList<>());
        user.setListOfPorscheCars(new ArrayList<>());

        // Retrieve roles from the repository
        Optional<UserRoleEntity> adminRole = userRoleRepository.findById(1L); // Assuming admin role ID is 1
        Optional<UserRoleEntity> userRole = userRoleRepository.findById(2L);  // Assuming user role ID is 2

        // Create a list to hold the roles
        List<UserRoleEntity> roles = new ArrayList<>();

        // Check if the email ends with ".admin" to determine role assignment
        if (userRegisterBindingModel.getEmail().endsWith(".admin")) {
            adminRole.ifPresent(roles::add);
        }

        // Add the user role regardless of email suffix
        userRole.ifPresent(roles::add);

        /* If user has admin role, also assign user role
        if (!roles.isEmpty() && roles.stream().anyMatch(role -> role.getId() == 1L)) {
            userRole.ifPresent(userRoleEntity -> roles.add(userRoleEntity));
        }*/

        // Set the roles for the user
        user.setRoles(roles);
        user.setActive(true);

        // Save the user to the repository
        userRepository.save(user);
    }
}
