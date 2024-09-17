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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

import jakarta.validation.Valid;

@Controller
@Validated
public class CurveController {
	
	private static final Logger logger = LoggerFactory.getLogger(CurveController.class);

	@Autowired
	private CurvePointService curvePointService;

	// Find all Curve Point, add to model
	@GetMapping("/curvepoint/list")
	public String home(Model model) {
		model.addAttribute("curvepoints", curvePointService.getAllCurvePoints());
		return "curvepoint/list";
	}

	@GetMapping("/curvepoint/add")
	public String addCurveForm(Model model) {
		model.addAttribute("curvepoint", new CurvePoint());
		return "curvepoint/add";
	}

	// Check data valid and save to db, after saving return Curve list
	@PostMapping("/curvepoint/validate")
	public String validate(@Valid com.nnk.springboot.domain.CurvePoint curvePoint, BindingResult result, Model model) {
		
		logger.info("Curve ID : " + curvePoint.getCurve_id());
	
		if (result.hasErrors()) {
			logger.error("Result for curve point has errors ", result.getAllErrors());			
			logger.info("Result error: Curve ID : " + curvePoint.getCurve_id());

			return "curvepoint/add";
		}
		curvePointService.saveCurvePoint(curvePoint);
		return "redirect:/curvepoint/list";
	}

	// Get CurvePoint by Id and to model then show to the form
	@GetMapping("/curvepoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
		if (curvePoint.isPresent()) {
			model.addAttribute("curvepoint", curvePoint.get());
			return "curvepoint/update";
		} else {
			return "redirect:/curvepoint/list";
		}
	}

	// Check required fields, if valid call service to update Curve and return
	// Curve list
	@PostMapping("/curvepoint/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @Valid com.nnk.springboot.domain.CurvePoint curvePoint, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("curvepoint", curvePoint);
			return "curvepoint/update";
		}
		curvePointService.updateCurvePoint(id, curvePoint);
		return "redirect:/curvepoint/list";
	}

	// Find Curve by Id and delete the Curve, return to Curve list
	@GetMapping("/curvepoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		curvePointService.deleteCurvePoint(id);
		return "redirect:/curvepoint/list";
	}
}
