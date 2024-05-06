package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.model.entity.enums.RoleEnum;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import com.example.java_spring_advanced_project.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleEntityRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleEntityRepository) {
        this.userRoleEntityRepository = userRoleEntityRepository;
    }

    @Override
    public void initRoles() {
        if (userRoleEntityRepository.count() == 0) {
            Arrays.stream(RoleEnum.values())
                    .forEach(roleEnum -> {
                        UserRoleEntity userRoleEntity = new UserRoleEntity();
                        userRoleEntity.setRole(roleEnum);
                        userRoleEntityRepository.save(userRoleEntity);
                    });
        }
    }
}