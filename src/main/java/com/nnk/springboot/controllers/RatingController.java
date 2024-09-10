package com.nnk.springboot.controllers;

import java.util.Optional;

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

	@Autowired
	private RatingService ratingService;

	@GetMapping("/rating/list")
	public String home(Model model) {
		model.addAttribute("ratings", ratingService.getAllRatings());
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		return "rating/add";
	}

	// check data valid and save to db, after saving return Rating list
	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		System.out.println("Order Number: " + rating.getOrder_number());

		if (result.hasErrors()) {
			model.addAttribute("rating", rating);
			return "rating/add";
		}
		ratingService.saveRating(rating);
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
		rating.setId(id);
		ratingService.saveRating(rating);
		return "redirect:/rating/list";
	}

	// Find Rating by Id and delete the Rating, return to Rating list
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		ratingService.deleteRating(id);
		return "redirect:/rating/list";
	}
}
