package com.lf.Orquestro.config;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lf.Orquestro.DomainModel.Session;
import com.lf.Orquestro.DomainModel.User;
import com.lf.Orquestro.DomainModel.enums.State;
import com.lf.Orquestro.repository.SessionRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SessionRepository sessionRepository;

    public TokenAuthenticationFilter(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        System.out.println("\n--- INICIANDO TOKEN AUTHENTICATION FILTER ---");
        
        String token = extractToken(request);

        if (token != null) {
            System.out.println("Token extraído do header: " + token);
            try {
            	Optional<Session> sessionOpt = sessionRepository.findBySessionTokenWithUserAndRoles(UUID.fromString(token));

                if (sessionOpt.isPresent()) {
                    Session session = sessionOpt.get();
                    System.out.println("Sessão encontrada para o token. ID da Sessão: " + session.getId());
                    
                    if (session.getState() == State.ACTIVE) {
                        System.out.println("Sessão está ATIVA. Autenticando usuário...");
                        User user = session.getUser();

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                .collect(Collectors.toList())
                        );
                        
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("Usuário '" + user.getName() + "' autenticado com sucesso no contexto de segurança.");
                    } else {
                        System.out.println("AVISO: Sessão encontrada, mas não está ativa. Estado: " + session.getState());
                    }
                } else {
                    System.out.println("AVISO: Nenhum registro de sessão encontrado para este token.");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("ERRO: Token com formato UUID inválido: " + token);
            }
        } else {
            System.out.println("Nenhum token 'Bearer' encontrado no cabeçalho Authorization.");
        }
        
        System.out.println("--- FINALIZANDO TOKEN AUTHENTICATION FILTER ---");
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}