package com.lf.Orquestro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lf.Orquestro.DomainModel.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

	List<Session> findByAccountId(Long accountId);

	Optional<Session> findBySessionToken(UUID sessionToken);

}
