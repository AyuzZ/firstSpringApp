package com.example.simplecrudproject.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/", "/products", "/products/index").permitAll()
                        .requestMatchers("/users", "/users/createUser").permitAll()
                        .requestMatchers("/login", "/logout").permitAll()
//                        .requestMatchers("/users/profile/**", "/users/updateUser/**").hasRole("USER")
//                        not required cause of .anyRequest other than mentioned require authentication
//                        and the authenticated is either admin or user
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/products/createProduct", "/products/editProduct/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/products", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                )
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(
                                new DelegatingSecurityContextRepository(
                                        new RequestAttributeSecurityContextRepository(),
                                        new HttpSessionSecurityContextRepository()))
                )
                .sessionManagement(session -> session
                        .sessionFixation().none()
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(true)
                )
        ;
        return http.build();
    }

    //Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //listener (to allow login only once)
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

    //ADMIN has all the access that the USER has
    @Bean
    static RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("USER")
                .build();
    }

}
