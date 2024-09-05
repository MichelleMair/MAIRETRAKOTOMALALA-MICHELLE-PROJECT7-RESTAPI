package com.nnk.springboot.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@Controller
public class CurveController {

	@Autowired
	private CurvePointService curvePointService;

	// Find all Curve Point, add to model
	@GetMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addCurveForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	// Check data valid and save to db, after saving return Curve list
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		}
		curvePointService.saveCurvePoint(curvePoint);
		return "redirect:/curvePoint/list";
	}

	// Get CurvePoint by Id and to model then show to the form
	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
		if (curvePoint.isPresent()) {
			model.addAttribute("curvePoint", curvePoint.get());
			return "curvePoint/update";
		} else {
			return "redirect:/curvePoint/list";
		}
	}

	// Check required fields, if valid call service to update Curve and return
	// Curve list
	@PostMapping("/curvePoint/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "curvePoint/update";
		}
		curvePoint.setId(id);
		curvePointService.saveCurvePoint(curvePoint);
		return "redirect:/curvePoint/list";
	}

	// Find Curve by Id and delete the Curve, return to Curve list
	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		curvePointService.deleteCurvePoint(id);
		return "redirect:/curvePoint/list";
	}
}
