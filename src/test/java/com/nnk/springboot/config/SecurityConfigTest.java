package com.nnk.springboot.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@Test
	@WithMockUser(username= "user", password= "Password123!" , roles= "USER")
	public void loginWithValidUserThenAuthenticated() throws Exception {
		mockMvc.perform(formLogin().user("user").password("Password123!")).andExpect(authenticated());
	}
	
	@Test
	public void loginWithInvalideUserThenUnauthenticated() throws Exception {
		mockMvc.perform(formLogin().user("invalid").password("invalide")).andExpect(unauthenticated());
	}
	
	@Test
	@WithMockUser(username= "admin", roles= {"ADMIN"})
	public void accessAdminPageWithAdminRole() throws Exception {
		mockMvc.perform(get("/user/list")).andExpect(authenticated()).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username= "user", roles= {"USER"})
	public void accessAdminPageWithUserRole_thenForbidden() throws Exception {
		mockMvc.perform(get("/user/list")).andExpect(status().isForbidden());
	}
	
	@Test
	public void logoutUser_thenUnauthenticated() throws Exception {
		mockMvc.perform(logout()).andExpect(unauthenticated()).andExpect(redirectedUrl("http://localhost/login"));
	}
}
