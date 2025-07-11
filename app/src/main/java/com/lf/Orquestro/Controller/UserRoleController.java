package com.lf.Orquestro.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lf.Orquestro.DomainModel.UserRole;
import com.lf.Orquestro.service.UserRoleService;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping
    public ResponseEntity<UserRole> createRole(@RequestBody UserRole userRole) {
        UserRole createdRole = userRoleService.createRole(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @GetMapping
    public ResponseEntity<List<UserRole>> getAllRoles() {
        List<UserRole> roles = userRoleService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getRoleById(@PathVariable Long id) {
        UserRole role = userRoleService.findRoleById(id);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRole> updateRole(@PathVariable Long id, @RequestBody UserRole roleDetails) {
        UserRole updatedRole = userRoleService.updateRole(id, roleDetails);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        userRoleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}