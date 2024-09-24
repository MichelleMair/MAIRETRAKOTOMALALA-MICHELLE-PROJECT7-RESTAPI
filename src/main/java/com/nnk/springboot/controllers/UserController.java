package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid com.nnk.springboot.domain.User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/add";
		}
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.saveUser(user);
			model.addAttribute("users", userService.getAllUsers());
			return "redirect:/user/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "user/add";
		}
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userService.getUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid com.nnk.springboot.domain.User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/update";
		}
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.updateUser(id, user);
			model.addAttribute("users", userService.getAllUsers());
			return "redirect:/user/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "user/update";
		}
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		try {
			userService.deleteUserById(id);
			model.addAttribute("users", userService.getAllUsers());
			return "redirect:/user/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/user/list";
		}
	}
}
