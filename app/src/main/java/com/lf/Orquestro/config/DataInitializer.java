package com.lf.Orquestro.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lf.Orquestro.DomainModel.UserRole;
import com.lf.Orquestro.repository.UserRoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    public DataInitializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Iniciando a verificação e criação de UserRoles padrão...");

        createRoleIfNotExists("User");
        createRoleIfNotExists("Administrator");
        createRoleIfNotExists("Developer");

        System.out.println("Finalizada a criação de UserRoles padrão.");
    }

    private void createRoleIfNotExists(String roleName) {
    	
        if (!userRoleRepository.findByName(roleName).isPresent()) {
            UserRole newRole = new UserRole(roleName);
            userRoleRepository.save(newRole);
            System.out.println("Criado o papel (role): " + roleName);
        }
    }
}