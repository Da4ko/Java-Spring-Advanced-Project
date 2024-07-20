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

    @Override
    public void giveAdmin(String uuid) {
        Optional<AdminApplication> adminApplication = adminApplicationsRepository.findById(uuid);
        UserEntity user = adminApplication.get().getApplicant();
        List<UserRoleEntity> roles = new ArrayList<>();
        UserRoleEntity adminRole = userRoleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Admin role not found"));
        roles.add(adminRole);
        userRepository.save(user);

        adminApplicationsRepository.deleteById(uuid);
    }

}
