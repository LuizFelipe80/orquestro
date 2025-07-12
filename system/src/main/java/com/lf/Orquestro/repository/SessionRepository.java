package com.lf.Orquestro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lf.Orquestro.DomainModel.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

	List<Session> findByUserId(Long userId);

	Optional<Session> findBySessionToken(UUID sessionToken);
	
	@Query("SELECT s FROM Session s JOIN FETCH s.user u JOIN FETCH u.roles WHERE s.sessionToken = :token")
	Optional<Session> findBySessionTokenWithUserAndRoles(@Param("token") UUID token);

}
