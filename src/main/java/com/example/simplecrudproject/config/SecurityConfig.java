package com.example.simplecrudproject.config;

import com.example.simplecrudproject.exception.CustomAccessDeniedHandler;
import com.example.simplecrudproject.model.Exceptions;
import jakarta.servlet.DispatcherType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(CustomAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/", "/products", "/products/index").permitAll()
                        .requestMatchers("/users", "/users/createUser").permitAll()
                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/users/profile/**", "/users/updateUser/**").hasRole("USER")
//                        not required cause of .anyRequest other than mentioned require authentication
//                        and the authenticated is either admin or user
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/products/createProduct", "/products/editProduct/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/products", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/products")
                        .invalidateHttpSession(true)
                        .addLogoutHandler(
                                new HeaderWriterLogoutHandler(
                                        new ClearSiteDataHeaderWriter(
                                                ClearSiteDataHeaderWriter.Directive.COOKIES)))
                )
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(
                                new DelegatingSecurityContextRepository(
                                        new RequestAttributeSecurityContextRepository(),
                                        new HttpSessionSecurityContextRepository()))
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler)
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
