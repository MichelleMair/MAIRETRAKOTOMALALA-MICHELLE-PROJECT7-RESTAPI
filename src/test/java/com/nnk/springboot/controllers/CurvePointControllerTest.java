package com.nnk.springboot.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
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
		mockMvc.perform(get("/curvepoint/add"))
		.andExpect(status().isOk())
		.andExpect(view().name("curvepoint/add"));
	}
	
	
	@Test
	public void testSaveCurvePointThrowsException() throws Exception {
		when(curvePointService.saveCurvePoint(Mockito.any(CurvePoint.class))).thenThrow(new RuntimeException("Saving curve point failed"));
		
		mockMvc.perform(post("/curvepoint/validate")
				.param("curve_id", "10")
				.param("term", "2.0")
				.param("value", "1.0"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("errorMessage"))
				.andExpect(model().attribute("errorMessage", "Saving curve point failed"))
				.andExpect(view().name("curvepoint/add"));
		
		verify(curvePointService, times(1)).saveCurvePoint(Mockito.any(CurvePoint.class));
	}

	@Test
	public void testUpdateCurvePoint() throws Exception {
		CurvePoint curvePoint = new CurvePoint(1, 10, null, 1.0, 2.0, null);
		when(curvePointService.getCurvePointById(1)).thenReturn(Optional.of(curvePoint));
		
		mockMvc.perform(get("/curvepoint/update/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("curvepoint/update"))
		.andExpect(model().attributeExists("curvepoint"));

		when(curvePointService.updateCurvePoint(Mockito.anyInt(), Mockito.any(CurvePoint.class))).thenReturn(curvePoint);

		mockMvc.perform(post("/curvepoint/update/1")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("curve_id", "10")
			.param("term", "2.0")
			.param("value", "1.5"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/curvepoint/list"));

		verify(curvePointService, times(1)).getCurvePointById(1);
		verify(curvePointService, times(1)).updateCurvePoint(Mockito.anyInt(), Mockito.any(CurvePoint.class));
	}
	
	
	@Test
	public void testUpdateCurvePointThrowsException() throws Exception {
		when(curvePointService.updateCurvePoint(Mockito.anyInt(), Mockito.any(CurvePoint.class))).thenThrow(new RuntimeException("Updating curve point failed"));
		
		mockMvc.perform(post("/curvepoint/update/1")
				.param("curve_id", "10")
				.param("term", "2.0")
				.param("value", "1.0"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("errorMessage"))
		.andExpect(model().attribute("errorMessage", "Updating curve point failed"))
		.andExpect(view().name("curvepoint/update"));
		
		verify(curvePointService, times(1)).updateCurvePoint(Mockito.anyInt(), Mockito.any(CurvePoint.class));
	}
	

	@Test
	public void testDeleteCurveById() throws Exception {
		mockMvc.perform(get("/curvepoint/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/curvepoint/list"));
		verify(curvePointService, times(1)).deleteCurvePoint(1);
	}
	
	@Test
	public void testDeleteCurvePointWithInvalidId() throws Exception {
		Mockito.doThrow(new RuntimeException("Curve point not found")).when(curvePointService).deleteCurvePoint(Mockito.anyInt());
		
		mockMvc.perform(get("/curvepoint/delete/999")).andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/curvepoint/list"));
	}
	
	@Test
	public void testUpdateCurvePointNotFound() throws Exception {
		when(curvePointService.getCurvePointById(1)).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/curvepoint/update/1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/curvepoint/list"));
	}
	
	@Test
	public void testValidateCurvePointWithMultipleErrors() throws Exception {
		mockMvc.perform(post("/curvepoint/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("curve_id", "")
				.param("term", "invalid")
				.param("value", "invalid"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("curvepoint", "curve_id", "term", "value"))
		.andExpect(view().name("curvepoint/add"));
	}

}
