package com.lf.Orquestro.Administration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lf.Orquestro.Administration.DomainModel.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}