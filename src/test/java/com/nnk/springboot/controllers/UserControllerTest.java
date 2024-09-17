package com.nnk.springboot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testHome() throws Exception {
		when(userService.getAllUsers()).thenReturn(List.of(new User("user", "password", "User", "USER")));

		mockMvc.perform(get("/user/list")).andExpect(status().isOk()).andExpect(model().attributeExists("users"))
				.andExpect(view().name("user/list"));
	}

	@Test
	public void testValidateUser() throws Exception {
		User user = new User("user", "Password@2024", "User", "USER");
		
		when(userService.saveUser(any(User.class))).thenReturn(user);
		
		when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

		mockMvc.perform(post("/user/validate")
				.param("username", "user")
				.param("password", "Password@2024")
				.param("fullname", "User")
				.param("role", "USER"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/user/list"));

		verify(userService).saveUser(any(User.class));
		verify(passwordEncoder).encode(any(String.class));

	}

	@Test
	public void testShowUpdateForm() throws Exception {
		User user = new User("user", "password", "User", "USER");

		when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));

		mockMvc.perform(get("/user/update/1")).andExpect(status().isOk()).andExpect(model().attributeExists("user"))
				.andExpect(view().name("user/update"));
	}

	@Test
	public void testUpdateUser() throws Exception {
		
		User user = new User("user", "Password@2024", "User", "USER");
		
		when(userService.updateUser(Mockito.anyInt(), Mockito.any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

		mockMvc.perform(post("/user/update/1")
				.param("username", "user")
				.param("password", "Password@2024")
				.param("fullname", "User")
				.param("role", "USER"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/user/list"));

		verify(userService).updateUser(Mockito.anyInt(), Mockito.any(User.class));
		verify(passwordEncoder).encode(any(String.class));
	}

	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(get("/user/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/user/list"));

		verify(userService).deleteUserById(1);
	}

}
