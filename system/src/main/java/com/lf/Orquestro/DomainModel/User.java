package com.lf.Orquestro.DomainModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

	private String fullName;
	private String name;
	private String password;
	private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role_assignments",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    public User() {
        super();
    }

    public User(String fullName, String name, String password, String email) {
        super();
        this.fullName = fullName;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<UserRole> getRoles() { return roles; }
    public void setRoles(Set<UserRole> roles) { this.roles = roles; }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return Objects.equals(email, other.email);
    }
}