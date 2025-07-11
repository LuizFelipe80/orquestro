package com.lf.Orquestro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lf.Orquestro.DomainModel.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	Optional<UserRole> findByName(String name);
}
