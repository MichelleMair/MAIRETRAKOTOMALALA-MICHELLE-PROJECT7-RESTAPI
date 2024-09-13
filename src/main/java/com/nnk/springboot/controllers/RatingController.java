package com.nnk.springboot.controllers;

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
	public String home(Model model) {
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
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		logger.info("Moodys: " + rating.getMoodys_rating());
		logger.info("SandPrating: " + rating.getSandprating());
		logger.info("Fitch rating: " + rating.getFitch_rating());
		logger.info("Order Number: " + rating.getOrder_number());
		
		if (result.hasErrors()) {
			logger.info("if result has errors: Moodys: " + rating.getMoodys_rating());
			logger.info("if result has errors: SandPrating: " + rating.getSandprating());
			logger.info("if result has errors: Fitch rating: " + rating.getFitch_rating());
			logger.info("if result has errors: Order Number: " + rating.getOrder_number());
			model.addAttribute("rating", rating);
			
			logger.info("if result has errors : " + rating);
			return "rating/add";
		}
		
		logger.info("if no errors, save rating  : " + rating);
		ratingService.saveRating(rating);
		
		logger.info("saving successfully   : " + rating);
		return "redirect:/rating/list";
	}

	// Get Rating by Id and to model then show to the form
	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Rating> rating = ratingService.getRatingById(id);
		if (rating.isPresent()) {
			model.addAttribute("rating", rating.get());
			return "rating/update";
		} else {
			return "redirect:/rating/list";
		}
	}

	// Check required fields, if valid call service to update Rating and return
	// rating list
	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("rating", rating);
			return "rating/update";
		}
		ratingService.updateRating(id, rating);
		return "redirect:/rating/list";
	}

	// Find Rating by Id and delete the Rating, return to Rating list
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		ratingService.deleteRating(id);
		return "redirect:/rating/list";
	}
}
