package com.colegio.sistema.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary; // Importar @Primary aquí también si tu UsuarioServiceImple también implementa UserDetailsService

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService; // Importar la interfaz
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService usuarioDetailsService; // Autowire la interfaz

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Configuración moderna para CSRF
            .csrf(csrf -> csrf.disable()) // Puedes habilitarlo y configurar si manejas tokens CSRF en el frontend

            .authorizeHttpRequests(authorize -> authorize // Cambiado a 'authorize' para claridad
                // Rutas permitidas para todos (sin autenticación)
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/api/usuarios").permitAll() // Añadido /img/**

                // Rutas protegidas por rol
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/profesor/inicio").hasRole("PROFESOR") // <--- ¡Asegúrate de que esta línea esté!
                .requestMatchers("/profesor/**").hasRole("PROFESOR") // Manten esta para otras posibles rutas del profesor
                .requestMatchers("/alumno/inicio").hasRole("ALUMNO") // <--- ¡Asegúrate de que esta línea esté para alumno!
                .requestMatchers("/alumno/**").hasRole("ALUMNO") // Manten esta para otras posibles rutas del alumno

                // Cualquier otra solicitud requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login") // URL de tu página de login personalizada
                .loginProcessingUrl("/login") // URL a la que el formulario de login envía los datos
                .defaultSuccessUrl("/redirigir-por-rol", true) // Redirige a este controlador después del login exitoso
                .permitAll() // Permite que todos accedan a la página de login
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // URL a la que redirigir después de cerrar sesión
                .permitAll() // Permite que todos realicen la acción de logout
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioDetailsService); // Usa el bean @Primary
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
