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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

import jakarta.validation.Valid;

@Controller
public class TradeController {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	private TradeService tradeService;

	@GetMapping("/trade/list")
	public String home(Model model) {
		model.addAttribute("trades", tradeService.getAllTrades());
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTradeForm(Model model) {
		model.addAttribute("trade", new Trade());
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("trade", trade);
			return "trade/add";
		}
		tradeService.saveTrade(trade);
		return "redirect:/trade/list";
	}

	// Get Trade by Id and to model then show to the form
	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Trade> trade = tradeService.getTradeById(id);
		if (trade.isPresent()) {
			model.addAttribute("trade", trade.get());
			return "trade/update";
		} else {
			return "redirect:/trade/list";
		}
	}

	// Check required fields, if valid call service to update Trade and return
	// Trade list
	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("trade", trade);
			return "trade/update";
		}
		trade.setTrade_id(id);
		tradeService.saveTrade(trade);
		return "redirect:/trade/list";
	}

	// Find Trade by Id and delete the Trade, return to Trade list
	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		tradeService.deleteTrade(id);
		return "redirect:/trade/list";
	}
}
