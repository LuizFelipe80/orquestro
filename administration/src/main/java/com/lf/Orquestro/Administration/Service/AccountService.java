package com.lf.Orquestro.Administration.Service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lf.Orquestro.Administration.DomainModel.Account;
import com.lf.Orquestro.Administration.Repository.AccountRepository;
import com.lf.Orquestro.DomainModel.enums.State;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
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
	public Account updateAccount(Long id, Account accountDetails) {
		Account existingAccount = findAccountById(id);

		existingAccount.setFullName(accountDetails.getFullName());
		existingAccount.setName(accountDetails.getName());
		existingAccount.setEmail(accountDetails.getEmail());
		existingAccount.setState(accountDetails.getState());

		return accountRepository.save(existingAccount);
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
	public Account restoreAccount(Long id) {
		Account account = findAccountById(id);
		if (account.getState() == State.TRASHED || account.getState() == State.INACTIVE) {
			account.setState(State.ACTIVE);
		}
		return accountRepository.save(account);
	}

	@Transactional
	@PreAuthorize("hasRole('DEVELOPER')")
	public void physicalDeleteAccount(Long id) {
		accountRepository.deleteById(id);
	}
}