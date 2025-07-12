package com.lf.Orquestro.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lf.Orquestro.repository.SessionRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final SessionRepository sessionRepository;

	public SecurityConfig(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    // Desabilitamos o CSRF
	    http.csrf(csrf -> csrf.disable());

	    // Configuramos o gerenciamento de sessão para ser stateless
	    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	    // Configuramos as regras de autorização
	    http.authorizeHttpRequests(auth -> {
	        auth.requestMatchers("/api/auth/login").permitAll();
	        auth.requestMatchers(HttpMethod.POST, "/api/users").permitAll(); // Deixando público por enquanto para testar
	        auth.anyRequest().authenticated();
	    });

	    // Adicionamos nosso filtro de token antes do filtro padrão
	    http.addFilterBefore(new TokenAuthenticationFilter(sessionRepository), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	@Bean
	public CommandLineRunner securityConfigCheck() {
	    return args -> {
	        System.out.println("\n\n******************************************************");
	        System.out.println("* *");
	        System.out.println("* >>> SE VOCÊ ESTÁ VENDO ISTO, SecurityConfig FOI CARREGADO! <<<   *");
	        System.out.println("* *");
	        System.out.println("******************************************************\n\n");
	    };
	}
}