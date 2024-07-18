package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void testMock(){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("Adi@Email");
        when(mockUserRepository.findByUsername("Adi"))
                .thenReturn(Optional.of(userEntity));

        Optional<UserEntity> userOpt = mockUserRepository.findByUsername("Adi");
       UserEntity user = userOpt.get();
        Assertions.assertEquals("Adi@Email", user.getEmail() );
    }
}
