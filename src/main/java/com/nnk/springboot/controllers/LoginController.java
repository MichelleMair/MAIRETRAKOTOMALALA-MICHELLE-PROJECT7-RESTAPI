package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

/**
 * LoginController handles login-related functionality
 * 
 * It responsible for managing login page, default redirect actions, post login
 * and error handling for unauthorized access. 
 * 
 */
@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Handles login page 
	 * 
	 * @return a ModelAndView object pointing to the login page view
	 * 
	 */
	@GetMapping("login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}
	
	/**
	 * Manages role based redirection after login 
	 * ADMIN users are redirected to the user management page
	 * USERS are redirected to the BidList page 
	 * 
	 * @param req the HttpServletRequest to retrieve the current user's role 
	 * @return the URL to which the user should be redirected based on their role 
	 * 
	 */
	@GetMapping("/default")
	public String defaultSuccessUrl(HttpServletRequest req) {
		if(req.isUserInRole("ADMIN")) {
			//spécifiques au rôle admin
			return "redirect:/user/list";
		} else if (req.isUserInRole("USER")){
			//Rôle user
			return "redirect:/bidlist/list";
		} else {
			//retour à la page login
			return "login";
		}
	}

	/**
	 * Displays a list of users for admin role
	 * This method is secured and only admin role can have access to it 
	 * 
	 * @return a ModelAndView object that contains user data 
	 */
	@GetMapping("secure/article-details")
	public ModelAndView getAllUserArticles() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userRepository.findAll());
		mav.setViewName("user/list");
		return mav;
	}

	/**
	 * Displays an error page when users try to access data they are not authorized to view 
	 * 
	 * Preventing unauthorized access 
	 * 
	 * @return a ModelAndView object containing an error message and pointing to the 403 page 
	 */
	@GetMapping("error")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView();
		String errorMessage = "You are not authorized for the requested data.";
		mav.addObject("errorMsg", errorMessage);
		mav.setViewName("403");
		return mav;
	}
}
