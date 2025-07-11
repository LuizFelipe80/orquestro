package com.lf.Orquestro.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lf.Orquestro.DomainModel.Account;
import com.lf.Orquestro.DomainModel.UserRole;
import com.lf.Orquestro.DomainModel.enums.State;
import com.lf.Orquestro.repository.AccountRepository;
import com.lf.Orquestro.repository.UserRoleRepository;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRoleRepository userRoleRepository;

	public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
			UserRoleRepository userRoleRepository) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
		this.userRoleRepository = userRoleRepository;
	}

	@Transactional
	public Account createAccount(Account account) {
		String hashedPassword = passwordEncoder.encode(account.getPassword());
		account.setPassword(hashedPassword);
		return accountRepository.save(account);
	}

	public Account findAccountById(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found for id: " + id));
	}

	public List<Account> findAllAccounts() {
		return accountRepository.findAll();
	}

	@Transactional
	public void deleteAccount(Long id) {
		Account account = findAccountById(id);
		if (account.getState() == State.ACTIVE) {
			account.setState(State.TRASHED);
		} else if (account.getState() == State.TRASHED) {
			account.setState(State.INACTIVE);
		}
		accountRepository.save(account);
	}

	@Transactional
	public Account updateAccount(Long id, Account accountDetails) {
		Account existingAccount = findAccountById(id);
		existingAccount.setFullName(accountDetails.getFullName());
		existingAccount.setName(accountDetails.getName());
		existingAccount.setEmail(accountDetails.getEmail());
		existingAccount.setState(accountDetails.getState());
		existingAccount.setActive(accountDetails.isActive());
		return accountRepository.save(existingAccount);
	}

	@Transactional
	public Account addRoleToAccount(Long accountId, Long roleId) {
		Account account = findAccountById(accountId);
		UserRole role = userRoleRepository.findById(roleId)
				.orElseThrow(() -> new RuntimeException("Role not found for id: " + roleId));

		account.getRoles().add(role);

		return accountRepository.save(account);
	}

	@Transactional
	public Account removeRoleFromAccount(Long accountId, Long roleId) {
		Account account = findAccountById(accountId);
		UserRole role = userRoleRepository.findById(roleId)
				.orElseThrow(() -> new RuntimeException("Role not found for id: " + roleId));

		account.getRoles().remove(role);

		return accountRepository.save(account);
	}
}