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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

import jakarta.validation.Valid;

@Controller
public class TradeController {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	private TradeService tradeService;

	@GetMapping("/trade/list")
	public String home(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			model.addAttribute("username", username);			
		}
		model.addAttribute("trades", tradeService.getAllTrades());
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTradeForm(Model model) {
		model.addAttribute("trade", new Trade());
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid com.nnk.springboot.domain.Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {} ", result.getAllErrors());
			
			model.addAttribute("org.springframework.validation.BindingResult.trade", result);
			model.addAttribute("trade", trade);
			return "trade/add";
		} 
		try {
			Trade addedTrade = tradeService.saveTrade(trade);
			
			logger.info("New trade was add successfully: " + addedTrade);
			return "redirect:/trade/list";
			
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "trade/add";
		}
	}

	// Get Trade by Id and to model then show to the form
	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Trade> trade = tradeService.getTradeById(id);
		if (trade.isPresent()) {
			logger.info("Processing updating for trade with ID: " +id);
			
			model.addAttribute("trade", trade.get());
			return "trade/update";
		} else {
			logger.error("No trade found with ID: "+ id);
			return "redirect:/trade/list";
		}
	}

	// Check required fields, if valid call service to update Trade and return
	// Trade list
	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid com.nnk.springboot.domain.Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {}" , result.getAllErrors());
			
			model.addAttribute("org.springframework.validation.BindingResult.trade", result);
			model.addAttribute("trade", trade);
			return "trade/update";
		} 
		try {
			Trade updatedTrade = tradeService.updateTrade(id, trade);
			
			logger.info("Updating trade successfully"+ updatedTrade);
			return "redirect:/trade/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "trade/update";
		}
	}

	// Find Trade by Id and delete the Trade, return to Trade list
	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		try {
			tradeService.deleteTrade(id);
			return "redirect:/trade/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/trade/list";
		}
	}
}
