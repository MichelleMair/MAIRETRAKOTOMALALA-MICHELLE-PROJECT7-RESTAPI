package com.nnk.springboot.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@ExtendWith(MockitoExtension.class)
public class CurvePointControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CurvePointService curvePointService;

	@InjectMocks
	private CurveController curveController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(curveController).build();
	}

	@Test
	public void testHome() throws Exception {
		when(curvePointService.getAllCurvePoints()).thenReturn(List.of(new CurvePoint(1, 10, null, 1.0, 2.0, null)));

		mockMvc.perform(get("/curvepoint/list")).andExpect(status().isOk())
				.andExpect(model().attributeExists("curvepoints")).andExpect(view().name("curvepoint/list"));

		verify(curvePointService, times(1)).getAllCurvePoints();
	}

	@Test
	public void testAddCurvePointForm() throws Exception {
		mockMvc.perform(get("/curvepoint/add")).andExpect(status().isOk()).andExpect(view().name("curvepoint/add"));
	}

	@Test
	public void testUpdateCurvePoint() throws Exception {
		CurvePoint curvePoint = new CurvePoint(1, 10, null, 1.0, 2.0, null);
		when(curvePointService.getCurvePointById(1)).thenReturn(Optional.of(curvePoint));

		mockMvc.perform(get("/curvepoint/update/1")).andExpect(status().isOk())
				.andExpect(view().name("curvepoint/update")).andExpect(model().attributeExists("curvepoint"));

		verify(curvePointService, times(1)).getCurvePointById(1);
	}

	@Test
	public void testDeleteCurveById() throws Exception {
		mockMvc.perform(get("/curvepoint/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvepoint/list"));
		verify(curvePointService, times(1)).deleteCurvePoint(1);
	}

}
