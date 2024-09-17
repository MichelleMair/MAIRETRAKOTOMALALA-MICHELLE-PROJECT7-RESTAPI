package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(Integer id, User updatedUser) {
		return userRepository.findById(id)
				.map(user -> {
					user.setUsername(updatedUser.getUsername());
					user.setPassword(updatedUser.getPassword());
					user.setRole(updatedUser.getRole());
					return userRepository.save(user);
				})
				.orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
	}
	
	public void deleteUserById(Integer id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("User with id " + id + " not found");
		}
	}
}
