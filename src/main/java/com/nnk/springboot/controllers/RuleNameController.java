package com.nnk.springboot.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

import jakarta.validation.Valid;

@Controller
@Validated
public class RuleNameController {
	
	private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameService ruleNameService;

	@GetMapping("/rulename/list")
	public String home(Model model) {
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
			model.addAttribute("rulename", ruleName);
			return "rulename/add";
		}
		ruleNameService.saveRuleName(ruleName);
		return "redirect:/rulename/list";
	}

	@GetMapping("/rulename/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<RuleName> rulename = ruleNameService.getRuleNameById(id);
		if (rulename.isPresent()) {
			model.addAttribute("rulename", rulename.get());
			return "rulename/update";
		} else {
			return "redirect:/rulename/list";
		}
	}

	@PostMapping("/rulename/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid com.nnk.springboot.domain.RuleName ruleName, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("rulename", ruleName);
			return "rulename/list";
		}
		ruleNameService.updateRuleName(id, ruleName);
		return "redirect:/rulename/list";
	}

	@GetMapping("/rulename/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		ruleNameService.deleteRuleName(id);
		return "redirect:/rulename/list";
	}
}
