package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.ChangeUsernameBindingModel;
import com.example.java_spring_advanced_project.model.binding.UserRegisterBindingModel;
import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import com.example.java_spring_advanced_project.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
       UserEntity userEntity = new UserEntity();

       userEntity.setUsername(userRegisterBindingModel.getUsername());
       userEntity.setEmail(userRegisterBindingModel.getEmail());
       userEntity.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
       userEntity.setListOfAudiCars(new ArrayList<>());
       userEntity.setListOfBmwCars(new ArrayList<>());
       userEntity.setListOfMercedesCars(new ArrayList<>());
       userEntity.setListOfPorscheCars(new ArrayList<>());


       // Retrieve roles from the repository
       UserRoleEntity adminRole = userRoleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Admin role not found")); // Assuming admin role ID is 1
       UserRoleEntity userRole = userRoleRepository.findById(2L).orElseThrow(() -> new RuntimeException("User role not found"));  // Assuming user role ID is 2

       List<UserRoleEntity> roles = new ArrayList<>();
       if (userRegisterBindingModel.getEmail().equals("admin@admin")) {
           roles.add(adminRole);
           roles.add(userRole); // Also add user role
       } else {
           roles.add(userRole);
       }

       userEntity.setRoles(roles);
       userEntity.setActive(true);

       System.out.println("User Entity before saving: " + userEntity);

       userRepository.save(userEntity);
   }

    @Override
    public boolean changeUsername(ChangeUsernameBindingModel changeUsernameBindingModel) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String currentUsername = ((UserDetails) principal).getUsername();
            UserEntity user = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));

            if (userRepository.existsByUsername(changeUsernameBindingModel.getNewUserName())) {
                return false;
            }

            user.setUsername(changeUsernameBindingModel.getNewUserName());
            userRepository.save(user);

            return true;
        }
        return false;
    }

}
