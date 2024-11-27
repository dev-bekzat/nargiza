package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/sign-in", "/sign-up", "/**", "/images/**").permitAll() // Разрешенные пути
                        .anyRequest().authenticated() // Все остальные требуют авторизации
                )
                .formLogin(form -> form
                        .loginPage("/sign-in") // Кастомная страница авторизации
                        .loginProcessingUrl("/perform-login") // URL для обработки формы
                        .defaultSuccessUrl("/home", true) // Успешный вход перенаправляет на /home
                        .failureUrl("/sign-in?error=true") // Ошибка авторизации
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода
                        .logoutSuccessUrl("/sign-in") // Куда перенаправлять после выхода
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Отключить CSRF для упрощения (рекомендуется включить в продакшене)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}