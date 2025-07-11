package com.lf.Orquestro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lf.Orquestro.DomainModel.UserRole;
import com.lf.Orquestro.repository.UserRoleRepository;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

  
    private String normalizeRoleName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do papel (role) não pode ser vazio.");
        }
        
        String cleaned = name.replaceAll("[^a-zA-Z\\s]", "");
        String trimmed = cleaned.trim();
        String normalized = trimmed.replaceAll("\\s+", "_");
        
        return normalized.toUpperCase();
    }

   
    @Transactional
    public UserRole createRole(UserRole userRole) {
        String normalizedName = normalizeRoleName(userRole.getName());
        
        Optional<UserRole> existingRole = userRoleRepository.findByName(normalizedName);
        if (existingRole.isPresent()) {
        	
            return existingRole.get();
        }
        
        userRole.setName(normalizedName);
        return userRoleRepository.save(userRole);
    }

    public UserRole findRoleById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole not found for id: " + id));
    }

    public List<UserRole> findAllRoles() {
        return userRoleRepository.findAll();
    }

   
    @Transactional
    public UserRole updateRole(Long id, UserRole roleDetails) {
        String normalizedName = normalizeRoleName(roleDetails.getName());

        Optional<UserRole> roleWithSameName = userRoleRepository.findByName(normalizedName);
        if (roleWithSameName.isPresent() && !roleWithSameName.get().getId().equals(id)) {
            throw new IllegalStateException("O nome de papel '" + normalizedName + "' já está em uso.");
        }

        UserRole existingRole = findRoleById(id);
        existingRole.setName(normalizedName);
        
        return userRoleRepository.save(existingRole);
    }

    
    @Transactional
    public void deleteRole(Long id) {
    	
        userRoleRepository.deleteById(id);
    }
}