package com.nnk.springboot.controllers;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import jakarta.validation.Valid;

@Controller
public class RatingController {
	
	private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;

	@GetMapping("/rating/list")
	public String home(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			model.addAttribute("username", username);
		}
		model.addAttribute("ratings", ratingService.getAllRatings());
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Model model) {
		model.addAttribute("rating", new Rating());
		return "rating/add";
	}

	// check data valid and save to db, after saving return Rating list
	@PostMapping("/rating/validate")
	public String validate(@Valid com.nnk.springboot.domain.Rating rating, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {} ", result.getAllErrors());
			model.addAttribute("org.springframework.validation.BindingResult.rating", result);
			model.addAttribute("rating", rating);

			return "rating/add";
		} 
		try {
	
			Rating addedRating= ratingService.saveRating(rating);
			logger.info("New rating was add successfully: " + addedRating);
			return "redirect:/rating/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "rating/add";
		}
	}

	// Get Rating by Id and to model then show to the form
	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Rating> rating = ratingService.getRatingById(id);
		if (rating.isPresent()) {
			logger.info("Processing updating a rating with ID: " + id);
			
			model.addAttribute("rating", rating.get());
			return "rating/update";
		} else {
			logger.error("No rating found with ID: " + id);
			return "redirect:/rating/list";
		}
	}

	// Check required fields, if valid call service to update Rating and return
	// rating list
	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid com.nnk.springboot.domain.Rating rating, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {}" , result.getAllErrors());
			
			model.addAttribute("org.springframework.validation.BindingResult.rating", result);
			model.addAttribute("rating", rating);
			return "rating/update";
		} 
		try {
				
			Rating updatedRating = ratingService.updateRating(id, rating);
			
			logger.info("Updating rating successfully" + updatedRating);
			return "redirect:/rating/list";
			
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "rating/update";
			}
		}

	// Find Rating by Id and delete the Rating, return to Rating list
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		ratingService.deleteRating(id);
		return "redirect:/rating/list";
	}
}
