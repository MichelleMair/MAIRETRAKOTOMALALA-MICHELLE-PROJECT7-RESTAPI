package com.nnk.springboot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@ExtendWith(MockitoExtension.class)
public class RuleNameControllerTest {

	private MockMvc mockMvc;

	@Mock
	private RuleNameService ruleNameService;

	@InjectMocks
	private RuleNameController ruleNameController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();
	}

	@Test
	public void testGetAllRuleNames() throws Exception {
		when(ruleNameService.getAllRuleNames()).thenReturn(
				List.of(new RuleName("Rule1", "Description1", "Json1", "Template1", "SQLSTR1", "SQLPart1")));

		mockMvc.perform(get("/rulename/list")).andExpect(status().isOk())
				.andExpect(model().attributeExists("rulenames")).andExpect(view().name("rulename/list"));
	}

	@Test
	public void testAddRuleForm() throws Exception {
		mockMvc.perform(get("/rulename/add")).andExpect(status().isOk()).andExpect(model().attributeExists("rulename"))
				.andExpect(view().name("rulename/add"));
	}

	@Test
	public void testValidate() throws Exception {
		RuleName ruleName = new RuleName("Rule1", "Description1", "Json1", "Template1", "SQLSTR1", "SQLPart1");

		when(ruleNameService.saveRuleName(any(RuleName.class))).thenReturn(ruleName);

		mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();

		mockMvc.perform(post("/rulename/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", "Rule1")
				.param("description", "Description1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/rulename/list"));

		verify(ruleNameService).saveRuleName(any(RuleName.class));
	}
	
	@Test
	public void testSaveRuleNameThrowsException() throws Exception {
		when(ruleNameService.saveRuleName(any(RuleName.class))).thenThrow(new RuntimeException("Saving rulename failed"));
	
		mockMvc.perform(post("/rulename/validate")
				.param("name", "Rule1")
				.param("description", "Description1"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("errorMessage"))
		.andExpect(model().attribute("errorMessage", "Saving rulename failed"))
		.andExpect(view().name("rulename/add"));
	
		verify(ruleNameService, times(1)).saveRuleName(any(RuleName.class));
	}

	@Test
	public void testShowUpdateForm() throws Exception {
		RuleName ruleName = new RuleName("Rule1", "Description1", "Json1", "Template1", "SQLSTR1", "SQLPart1");

		when(ruleNameService.getRuleNameById(anyInt())).thenReturn(Optional.of(ruleName));

		mockMvc.perform(get("/rulename/update/1")).andExpect(status().isOk())
				.andExpect(model().attributeExists("rulename"))
				.andExpect(view().name("rulename/update"));
	}

	@Test
	public void testUpdateRuleName() throws Exception {
		RuleName rulename = new RuleName("Rule1", "Description1", "Json1", "Template1", "SQLSTR1", "SQLPart1");
		
		when(ruleNameService.updateRuleName(Mockito.anyInt(), Mockito.any(RuleName.class))).thenReturn(rulename);
		
		mockMvc.perform(post("/rulename/update/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", "Rule1")
				.param("description", "Description1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/rulename/list"));

		verify(ruleNameService).updateRuleName(Mockito.anyInt(), Mockito.any(RuleName.class));
	}
	
	@Test
	public void testUpdateRuleNameThrowsException() throws Exception {
		when(ruleNameService.updateRuleName(Mockito.anyInt(), any(RuleName.class))).thenThrow(new RuntimeException("Updating rating failed"));
		
		mockMvc.perform(post("/rulename/update/1")
				.param("name", "Rule1")
				.param("description", "Description1"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("errorMessage"))
		.andExpect(model().attribute("errorMessage", "Updating rating failed"))
		.andExpect(view().name("rulename/update"));
		
		verify(ruleNameService, times(1)).updateRuleName(Mockito.anyInt(), Mockito.any(RuleName.class));
	}
	
	@Test
	public void testUpdateRulenameWithInvalidId() throws Exception {
		when(ruleNameService.getRuleNameById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/rulename/update/999"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/rulename/list"));
	}

	@Test
	public void testDeleteRuleName() throws Exception {
		mockMvc.perform(get("/rulename/delete/1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/rulename/list"));

		verify(ruleNameService).deleteRuleName(anyInt());
	}
	
	@Test
	public void testDeleteRulenameNotFound() throws Exception {
		mockMvc.perform(get("/rulename/delete/99"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/rulename/list"));
	}
	
	@Test
	public void testDeleteRuleNameWithInvalidId() throws Exception {
		doThrow(new RuntimeException("Trade not found")).when(ruleNameService).deleteRuleName(Mockito.anyInt());
		
		mockMvc.perform(get("/rulename/delete/999"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/rulename/list"));
	}

}
