package com.integrador.empleau.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



import com.integrador.empleau.service.UsuarioDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     private final UsuarioDetailsServiceImpl usuarioDetailsService;

    public SecurityConfig(UsuarioDetailsServiceImpl usuarioDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/alumnos/registroAlumno/**", "/login",
                                "/empresas/registroEmpresa/**", "/home", "/alumnos/registrarAlumno",
                                "/empresas/registrarEmpresa", "/")
                        .permitAll()
                        .requestMatchers("/alumnos/**").hasRole("ALUMNO")
                        .requestMatchers("/empresas/**").hasRole("EMPRESA")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // Ruta de tu formulario personalizado
                        .successHandler((request, response, authentication) -> {
                            // Redirige según el rol
                            if (tieneRol(authentication, "ADMINISTRADOR")) {
                                response.sendRedirect("/admin/dashboard");
                            } else if (tieneRol(authentication, "EMPRESA")) {
                                response.sendRedirect("/empresas/perfilEmpresa");
                            } else {
                                response.sendRedirect("/alumnos/perfilAlumno");
                            }
                        }) // Redirección tras login
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    // Método auxiliar para verificar roles
        private boolean tieneRol(Authentication auth, String rol) {
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService((org.springframework.security.core.userdetails.UserDetailsService) usuarioDetailsService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   
}
