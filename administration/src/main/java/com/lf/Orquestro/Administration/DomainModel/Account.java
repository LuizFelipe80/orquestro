package com.lf.Orquestro.Administration.DomainModel;

import com.lf.Orquestro.DomainModel.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public class Account extends User {
	private static final long serialVersionUID = 1L;

	public Account() {
		super();
	}

	public Account(String fullName, String name, String password, String email) {
		super(fullName, name, password, email);
	}
}