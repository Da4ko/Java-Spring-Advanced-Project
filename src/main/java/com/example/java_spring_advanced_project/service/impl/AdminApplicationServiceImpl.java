package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.ApplicationSubmitBindingModel;
import com.example.java_spring_advanced_project.model.dto.Applications.ApplicationDto;
import com.example.java_spring_advanced_project.model.dto.HomeAdminApplicationDto;
import com.example.java_spring_advanced_project.model.entity.AdminApplication;
import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.repository.AdminApplicationsRepository;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import com.example.java_spring_advanced_project.service.AdminApplicationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AdminApplicationServiceImpl implements AdminApplicationService {

    private final AdminApplicationsRepository adminApplicationsRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public AdminApplicationServiceImpl(AdminApplicationsRepository adminApplicationsRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.adminApplicationsRepository = adminApplicationsRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public HomeAdminApplicationDto getApplicationsForBugsHome() {
        List<AdminApplication> applications = adminApplicationsRepository.findAll();

        List<ApplicationDto> applicationDtoList = new ArrayList<>();

        for (AdminApplication adminApplication : applications) {
            applicationDtoList.add(new ApplicationDto(adminApplication));
        }

        HomeAdminApplicationDto homeAdminApplicationDto = new HomeAdminApplicationDto(applicationDtoList);

        return homeAdminApplicationDto;
    }




    @Override
    public boolean create(ApplicationSubmitBindingModel applicationSubmitBindingModel) {
        AdminApplication adminApplication = new AdminApplication();
        adminApplication.setDescription(applicationSubmitBindingModel.getDescription());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();

            Optional<UserEntity> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                adminApplication.setApplicant(user);
            } else {
                throw new UsernameNotFoundException("User not found: " + username);
            }
        }
        try {
            adminApplicationsRepository.save(adminApplication);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public void deleteApplication(String uuid) {
        adminApplicationsRepository.deleteById(uuid);
    }
//TODO
 /*   @Override
    public void giveAdmin(String uuid) {
        Optional<AdminApplication> adminApplication = adminApplicationsRepository.findById(uuid);
        UserEntity user = adminApplication.get().getApplicant();
        if(user.getRoles().size() == 2){
            userRepository.save(user);
        }else {
            List<UserRoleEntity> roles = new ArrayList<>();
            UserRoleEntity adminRole = userRoleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Admin role not found"));
            UserRoleEntity userRole = userRoleRepository.findById(2L).orElseThrow(() -> new RuntimeException("User role not found"));
            roles.add(userRole);
            roles.add(adminRole);
            userRepository.save(user);

            adminApplicationsRepository.deleteById(uuid);
        }
    }*/
@Override
public void giveAdmin(String uuid) {
    Optional<AdminApplication> adminApplicationOptional = adminApplicationsRepository.findById(uuid);

    if (adminApplicationOptional.isEmpty()) {
        throw new RuntimeException("Admin application not found for UUID: " + uuid);
    }

    AdminApplication adminApplication = adminApplicationOptional.get();
    UserEntity user = adminApplication.getApplicant();


    // Check if the user already has the admin role
    boolean hasAdminRole = user.getRoles().stream()
            .anyMatch(role -> role.getRole().toString().equals("admin"));  // Assuming role names are used

    // If the user already has the admin role, delete the admin application
    if (hasAdminRole) {
        adminApplicationsRepository.deleteById(uuid);
        return;
    }

    // Fetch the admin role
    UserRoleEntity adminRole = userRoleRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("Admin role not found"));

    // Add the admin role to the user's existing roles
    user.getRoles().add(adminRole);

    // Save the updated user entity
    userRepository.save(user);

    // Delete the admin application
    adminApplicationsRepository.deleteById(uuid);
}


}
