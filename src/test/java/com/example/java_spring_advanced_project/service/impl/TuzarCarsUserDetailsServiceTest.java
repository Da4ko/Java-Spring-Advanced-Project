package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.model.entity.enums.RoleEnum;
import com.example.java_spring_advanced_project.repository.UserRepository;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TuzarCarsUserDetailsServiceTest {

    private TuzarCarsUserDetailsService serviceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        serviceToTest = new TuzarCarsUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("Batman"));

    }
    @Test
    void userFound(){
        UserEntity testUserEntity = createTestUser();
        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).thenReturn(Optional.of(testUserEntity));

        UserDetails userDetails = serviceToTest.loadUserByUsername(testUserEntity.getUsername());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(testUserEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
    }
    private static UserEntity createTestUser(){
        UserEntity user = new UserEntity();
        user.setUsername("Batman");
        user.setPassword("123123");
        user.setEmail("Batman@Batman.admin");
        user.setActive(false);
        user.setRoles(List.of(
                new UserRoleEntity().setRole(RoleEnum.admin),
                new UserRoleEntity().setRole(RoleEnum.user)
        ));
        return user;
    }
}
