package com.lf.Orquestro.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lf.Orquestro.DomainModel.Session;
import com.lf.Orquestro.dto.LoginRequest;
import com.lf.Orquestro.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	public ResponseEntity<Session> login(@RequestBody LoginRequest loginRequest,
			@RequestHeader("User-Agent") String userAgent, jakarta.servlet.http.HttpServletRequest request) {

		String ipAddress = request.getRemoteAddr();

		Session session = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword(), ipAddress,
				userAgent);

		return ResponseEntity.ok(session);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return ResponseEntity.badRequest().build();
		}

		String sessionToken = authorizationHeader.substring(7);

		authenticationService.logout(sessionToken);

		return ResponseEntity.ok().build();
	}
}