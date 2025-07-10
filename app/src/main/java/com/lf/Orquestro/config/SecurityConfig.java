package com.lf.Orquestro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt é o padrão da indústria para hashing de senhas.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita o CSRF. Para uma API REST stateless, isso é comum.
            // Para aplicações web com formulários, seria necessário configurar.
            .csrf(csrf -> csrf.disable())
            // Define as regras de autorização para as requisições
            .authorizeHttpRequests(auth -> auth
                // Permite que qualquer um (autenticado ou não) acesse nossos endpoints de contas
                .requestMatchers("/api/accounts/**").permitAll()
                // Exige autenticação para qualquer outra requisição
                .anyRequest().authenticated()
            );

        return http.build();
    }
}