package com.lf.Orquestro.DomainModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

// REMOVIDO o import do JsonBackReference

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "model_guid", nullable = false, unique = true)
    private UUID modelGUID;

    // --- CAMPO E RELACIONAMENTO COM USER/ACCOUNT REMOVIDO DAQUI ---
    // A linha "@JsonBackReference private Set<User> users" foi apagada.

    @ManyToMany
    @JoinTable(
        name = "role_grantable_roles",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "grantable_role_id")
    )
    private Set<UserRole> grantableRoles = new HashSet<>();

    public UserRole() {
        this.modelGUID = UUID.randomUUID();
    }

    public UserRole(String name) {
        this();
        this.name = name;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public UUID getModelGUID() { return modelGUID; }
    public void setModelGUID(UUID modelGUID) { this.modelGUID = modelGUID; }
    public Set<UserRole> getGrantableRoles() { return grantableRoles; }
    public void setGrantableRoles(Set<UserRole> grantableRoles) { this.grantableRoles = grantableRoles; }

    // --- GETTER E SETTER PARA USERS/ACCOUNTS REMOVIDO ---

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UserRole other = (UserRole) obj;
        return Objects.equals(name, other.name);
    }
}