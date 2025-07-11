package com.lf.Orquestro.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lf.Orquestro.DomainModel.User;
import com.lf.Orquestro.DomainModel.UserRole;
import com.lf.Orquestro.repository.UserRepository;
import com.lf.Orquestro.service.UserRoleService;
import com.lf.Orquestro.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

	private final UserRoleService userRoleService;
	private final UserService userService;
	private final UserRepository userRepository;

	public DataInitializer(UserRoleService userRoleService, UserService userService, UserRepository userRepository) {
		this.userRoleService = userRoleService;
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Iniciando a criação de dados padrão...");

		UserRole userRole = userRoleService.createRole(new UserRole("User"));
		UserRole adminRole = userRoleService.createRole(new UserRole("Administrator"));
		UserRole devRole = userRoleService.createRole(new UserRole("Developer"));

		createTestUserIfNotExists("demo_user", userRole);
		createTestUserIfNotExists("demo_administrator", adminRole);
		createTestUserIfNotExists("demo_developer", devRole);

		System.out.println("Finalizada a criação de dados padrão.");
	}

	private void createTestUserIfNotExists(String username, UserRole role) {
		if (!userRepository.findByName(username).isPresent()) {

			if (role != null) {
				User newUser = new User();
				newUser.setName(username);
				newUser.setFullName(username);
				newUser.setEmail(username + "@orquestro.com");
				newUser.setPassword(username);

				newUser.getRoles().add(role);

				userService.createUser(newUser);
				System.out.println("Criado o usuário de teste: " + username + " com o papel " + role.getName());
			} else {
				System.out.println("AVISO: Papel nulo fornecido. O usuário " + username + " não pôde ser criado.");
			}
		}
	}
}