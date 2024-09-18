package com.nnk.springboot.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	private User user;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		user = new User(1, "Usertest", "Password123!", "User Test", "USER");
	}
	
	@Test
	public void testGetAllUsers() {
		List<User> users = List.of(user);
		
		when(userRepository.findAll()).thenReturn(users);
		
		List<User> result = userService.getAllUsers();
		
		assertEquals(1, result.size());
		assertEquals("Usertest", result.get(0).getUsername());
		verify(userRepository, times(1)).findAll();
	}
	
	@Test
	public void testGetUserById_UserFound() {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		
		Optional<User> result = userService.getUserById(1);
		
		assertTrue(result.isPresent());
		assertEquals("Usertest", result.get().getUsername());
		verify(userRepository, times(1)).findById(1);
	}
	
	@Test
	public void testGetUserById_UserNotFound() {
		when(userRepository.findById(1)).thenReturn(Optional.empty());
		
		Optional<User> result = userService.getUserById(1);
		
		assertFalse(result.isPresent());
		verify(userRepository, times(1)).findById(1);
	}
	
	
	@Test
	public void testSaveUser() {
		when(userRepository.save(user)).thenReturn(user);
		
		User savedUser = userService.saveUser(user);
		
		assertEquals("Usertest", savedUser.getUsername());
		verify(userRepository, times(1)).save(user);
	}
	
	
	@Test
	public void testUpdateUser_UserFound() {
		User updatedUser = new User(1, "Userupdated", "NewPassword123!", "User Updated", "ADMIN");
		
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);
		
		User result = userService.updateUser(1, updatedUser);
		
		assertEquals("Userupdated", result.getUsername());
		assertEquals("ADMIN", result.getRole());
		verify(userRepository, times(1)).findById(1);
		verify(userRepository, times(1)).save(user);
	}
	
	
	@Test
	public void testUpdateUser_UserNotFound() {
		User updatedUser = new User(1, "Userupdated", "NewPassword123!", "User Updated", "ADMIN");
		
		when(userRepository.findById(1)).thenReturn(Optional.empty());
		
		EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
			userService.updateUser(1, updatedUser);
		});
		
		assertEquals("User with id 1 not found", ex.getMessage());
		verify(userRepository, times(1)).findById(1);
		verify(userRepository, never()).save(any(User.class));
	}
	
	
	@Test
	public void testDeleteUserById_UserFound() {
		when(userRepository.existsById(1)).thenReturn(true);
		
		userService.deleteUserById(1);
		
		verify(userRepository, times(1)).deleteById(1);
	}
	
	@Test
	public void testDeletUserById_UserNotFound() {
		when(userRepository.existsById(1)).thenReturn(false);
		
		EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
			userService.deleteUserById(1);
		});
		assertEquals("User with id 1 not found", ex.getMessage());
		verify(userRepository, never()).deleteById(1);
	}
}
