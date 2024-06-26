package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class TuzarCarsUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public TuzarCarsUserDetailsService(UserRepository userRepository){

        this.userRepository = userRepository;
    }
   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepository
                .findByUsername(username)
                .map(TuzarCarsUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));



    }
   private static UserDetails map(UserEntity userEntity){
      return User
                 .withUsername(userEntity.getUsername())
                 .password(userEntity.getPassword())
                 .authorities(userEntity.getRoles().stream().map(TuzarCarsUserDetailsService::map).toList())
                 .build();
    }

   private static GrantedAuthority map(UserRoleEntity userRoleEntity){
        return new SimpleGrantedAuthority(
                "ROLE_" + userRoleEntity.getRole().name()
        );
   }
}
