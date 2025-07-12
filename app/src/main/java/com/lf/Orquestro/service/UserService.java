package com.lf.Orquestro.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lf.Orquestro.DomainModel.User;
import com.lf.Orquestro.DomainModel.UserRole;
import com.lf.Orquestro.DomainModel.enums.State;
import com.lf.Orquestro.repository.UserRepository;
import com.lf.Orquestro.repository.UserRoleRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public User createUser(User user) {
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		return userRepository.save(user);
	}

	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found for id: " + id));
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void deleteUser(Long id) {
		User user = findUserById(id);
		if (user.getState() == State.ACTIVE) {
			user.setState(State.TRASHED);
		} else if (user.getState() == State.TRASHED) {
			user.setState(State.INACTIVE);
		}
		userRepository.save(user);
	}

	@Transactional
	public User updateUser(Long id, User userDetails) {
		User existingUser = findUserById(id);
		existingUser.setFullName(userDetails.getFullName());
		existingUser.setName(userDetails.getName());
		existingUser.setEmail(userDetails.getEmail());
		existingUser.setState(userDetails.getState());
		return userRepository.save(existingUser);
	}

	@Transactional
	public User addRoleToUser(Long userId, Long roleId) {
		User user = findUserById(userId);
		UserRole role = userRoleRepository.findById(roleId)
				.orElseThrow(() -> new RuntimeException("Role not found for id: " + roleId));
		user.getRoles().add(role);
		return userRepository.save(user);
	}

	@Transactional
	public User removeRoleFromUser(Long userId, Long roleId) {
		User user = findUserById(userId);
		UserRole role = userRoleRepository.findById(roleId)
				.orElseThrow(() -> new RuntimeException("Role not found for id: " + roleId));
		user.getRoles().remove(role);
		return userRepository.save(user);
	}

	@Transactional
	@PreAuthorize("hasRole('DEVELOPER')")
	public void physicalDeleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new RuntimeException("User not found for id: " + id);
		}
		userRepository.deleteById(id);
	}
}