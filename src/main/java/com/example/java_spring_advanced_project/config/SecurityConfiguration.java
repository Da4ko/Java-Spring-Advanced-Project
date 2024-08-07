package com.example.java_spring_advanced_project.config;

import com.example.java_spring_advanced_project.model.entity.enums.RoleEnum;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.service.impl.TuzarCarsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/","/users/login","users/register", "/users/login-error",
                                "/about-us", "/changed-username").permitAll()

                        .requestMatchers("/home", "/audi/audi-cars-home", "/bmw/bmw-cars-home"
                        , "/mercedes/mercedes-cars-home", "/porsche/porsche-cars-home"
                        ,"/audi/add-audi", "/bmw/add-bmw", "/porsche/add-porsche"
                        , "/mercedes/add-mercedes", "/bugs/add-report", "/bugs/thank-you", "/apply/make-application").hasRole(RoleEnum.user.name())

                        .requestMatchers("/error").permitAll()

                        .requestMatchers("bugs/reported-bugs", "/apply/view-applications").hasRole(RoleEnum.admin.name())
                        .anyRequest().authenticated()

        ).formLogin(
                formLogin -> {
                    formLogin.loginPage("/users/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/home")

                            .failureHandler(customAuthenticationFailureHandler);
                }
        ).logout(
                logout -> {
                    logout.logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        );

        return httpSecurity.build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new TuzarCarsUserDetailsService(userRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

