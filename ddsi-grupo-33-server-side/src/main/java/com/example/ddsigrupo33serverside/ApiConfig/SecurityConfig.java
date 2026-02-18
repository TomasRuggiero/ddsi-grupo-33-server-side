package com.example.ddsigrupo33serverside.ApiConfig;

import com.example.ddsigrupo33serverside.Providers.CustomAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

  @Bean
  public AuthenticationManager authManager(HttpSecurity http, CustomAuthProvider provider) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(provider)
        .build();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/error403", "/error401", "/error404", "/login", "/css/**", "/js/**", "/",
                    "/register", "/favicon.ico", "/hechos/nuevo").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/visualizador/**", "/estadisticas/**").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")    // tu template de login
            .permitAll()
            .defaultSuccessUrl("/", true) // redirigir tras login exitoso
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout") // redirigir tras logout
            .permitAll()
        )
        .exceptionHandling(ex -> ex
            // Usuario no autenticado → redirigir a login
            .authenticationEntryPoint((request, response, authException) ->
                response.sendRedirect("/login?unauthorized")
            )
            // Usuario autenticado pero sin permisos → redirigir a página de error
            .accessDeniedHandler((request, response, accessDeniedException) ->
                response.sendRedirect("/error403")
            )
        );

    return http.build();
  }
}
