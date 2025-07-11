package com.lf.Orquestro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lf.Orquestro.DomainModel.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByName(String name);

}
