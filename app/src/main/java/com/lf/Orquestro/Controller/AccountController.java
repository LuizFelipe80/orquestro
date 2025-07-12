package com.lf.Orquestro.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lf.Orquestro.Administration.DomainModel.Account;
import com.lf.Orquestro.Administration.Service.AccountService;

@RestController
@RequestMapping("/api/admin/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account createdAccount = accountService.createAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
	}

	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountService.findAllAccounts();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
		Account account = accountService.findAccountById(id);
		return ResponseEntity.ok(account);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
		Account updatedAccount = accountService.updateAccount(id, accountDetails);
		return ResponseEntity.ok(updatedAccount);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/restore")
	public ResponseEntity<Account> restoreAccount(@PathVariable Long id) {
		Account restoredAccount = accountService.restoreAccount(id);
		return ResponseEntity.ok(restoredAccount);
	}

	@DeleteMapping("/{id}/physical")
	public ResponseEntity<Void> physicalDeleteAccount(@PathVariable Long id) {
		accountService.physicalDeleteAccount(id);
		return ResponseEntity.noContent().build();
	}
}