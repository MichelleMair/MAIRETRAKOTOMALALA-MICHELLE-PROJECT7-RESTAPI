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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

import jakarta.validation.Valid;

@Controller
public class RuleNameController {
	
	private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameService ruleNameService;

	@GetMapping("/rulename/list")
	public String home(Model model, Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			model.addAttribute("username", username);
		}
		model.addAttribute("rulenames", ruleNameService.getAllRuleNames());
		return "rulename/list";
	}

	@GetMapping("/rulename/add")
	public String addRuleForm(Model model) {
		model.addAttribute("rulename", new RuleName());
		return "rulename/add";
	}

	@PostMapping("/rulename/validate")
	public String validate(@Valid com.nnk.springboot.domain.RuleName ruleName, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {} ", result.getAllErrors());
			
			model.addAttribute("org.springframework.validation.BindingResult.rulename", result);
			model.addAttribute("rulename", ruleName);
			return "rulename/add";
		} 
		try {
			RuleName addedRulename = ruleNameService.saveRuleName(ruleName);
			
			logger.info("New RuleName was add successfully: " + addedRulename);
			return "redirect:/rulename/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "rulename/add";
		}
	}

	@GetMapping("/rulename/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<RuleName> rulename = ruleNameService.getRuleNameById(id);
		if (rulename.isPresent()) {
			logger.info("Processing updating for rulename with id: " + id);
			
			model.addAttribute("rulename", rulename.get());
			return "rulename/update";
		} else {
			logger.error("No rulename found with ID: " + id);
			return "redirect:/rulename/list";
		}
	}

	@PostMapping("/rulename/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid com.nnk.springboot.domain.RuleName ruleName, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			logger.error("Validation errors occured: {}" , result.getAllErrors());
			
			model.addAttribute("org.springframework.validation.BindingResult.rulename", result);
			model.addAttribute("rulename", ruleName);
			return "rulename/update";
		} 
		try {
			RuleName updatedRulename = ruleNameService.updateRuleName(id, ruleName);
			
			logger.info("Updating rulename successfully: " + updatedRulename);
			return "redirect:/rulename/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "rulename/update";
		}
	}

	@GetMapping("/rulename/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		try {
			ruleNameService.deleteRuleName(id);
			return "redirect:/rulename/list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/rulename/list";
		}
	}
}
