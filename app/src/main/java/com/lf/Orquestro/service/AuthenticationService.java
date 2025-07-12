package com.lf.Orquestro.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lf.Orquestro.DomainModel.Session;
import com.lf.Orquestro.DomainModel.User;
import com.lf.Orquestro.DomainModel.enums.State;
import com.lf.Orquestro.repository.SessionRepository;
import com.lf.Orquestro.repository.UserRepository;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;
	private final SessionRepository sessionRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationService(UserRepository userRepository, SessionRepository sessionRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.sessionRepository = sessionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Session login(String username, String password, String ipAddress, String userAgent) {
		User user = userRepository.findByName(username)
				.orElseThrow(() -> new SecurityException("Credenciais inválidas: usuário não encontrado."));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new SecurityException("Credenciais inválidas: senha incorreta.");
		}

		Session newSession = new Session(user, ipAddress, userAgent);

		return sessionRepository.save(newSession);
	}

	@Transactional
	public void logout(String sessionToken) {
		UUID token;
		try {
			token = UUID.fromString(sessionToken);
		} catch (IllegalArgumentException e) {
			throw new SecurityException("Formato de token inválido.");
		}

		Session session = sessionRepository.findBySessionToken(token)
				.orElseThrow(() -> new SecurityException("Sessão não encontrada ou já encerrada."));

		if (session.getState() != State.ACTIVE) {
			throw new SecurityException("Sessão não encontrada ou já encerrada.");
		}

		session.setState(State.INACTIVE);
		session.setLogoutTime(java.time.LocalDateTime.now());

		sessionRepository.save(session);
	}
}