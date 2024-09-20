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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import jakarta.validation.Valid;

@Controller
public class BidListController {
	
	private static final Logger logger = LoggerFactory.getLogger(BidListController.class);

	@Autowired
	private BidListService bidListService;

	// Call service find all bids to show to the view
	@GetMapping("/bidlist/list")
	public String home(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			model.addAttribute("username", username);
		}
		model.addAttribute("bidlists", bidListService.getAllBidLists());
		return "bidlist/list";
	}

	@GetMapping("/bidlist/add")
	public String addBidForm(Model model) {
		model.addAttribute("bidlist", new BidList());
		return "bidlist/add";
	}

	// Check data valid and save to db, after saving return bid list
	@PostMapping("/bidlist/validate")
	public String validate(@Valid com.nnk.springboot.domain.BidList bid, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {} ", result.getAllErrors());
			model.addAttribute("org.springframework.validation.BindingResult.bidlist", result);
			model.addAttribute("bidlist", bid);
			return "bidlist/add";
		} 
		try {
			bidListService.saveBidList(bid);
			
			logger.info("New bid was add successfully: " + bidListService.saveBidList(bid));
			return "redirect:/bidlist/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "bidlist/add";
		}
	}

	// Get Bid by Id and to model then show to the form
	@GetMapping("/bidlist/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<BidList> bidList = bidListService.getBidListById(id);
		if (bidList.isPresent()) {
			
			logger.info("Processing updating for bid with ID : " + id);
			
			model.addAttribute("bidlist", bidList.get());
			return "bidlist/update";
		} else {
			logger.error("No bid found with ID : " + id);
			return "redirect:/bidlist/list";
		}
	}

	// Check required fields, if valid call service to update Bid and return
	// list Bid
	@PostMapping("/bidlist/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid com.nnk.springboot.domain.BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {}" , result.getAllErrors());
			
			model.addAttribute("org.springframework.validation.BindingResult.bidlist", result);
			model.addAttribute("bidlist", bidList);
			return "bidlist/update";
			
		} 
		try {
				
			BidList updatedBid = bidListService.updateBidList(id, bidList);
			
			logger.info("Updating bid successfully: " + updatedBid);
			return "redirect:/bidlist/list";
		} catch (RuntimeException e){
			model.addAttribute("errorMessage", e.getMessage());
			return "bidlist/update";
		}
	}

	// Find Bid by Id and delete the bid, return to Bid list
	@GetMapping("/bidlist/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		bidListService.deleteBidList(id);
		return "redirect:/bidlist/list";
	}
}
