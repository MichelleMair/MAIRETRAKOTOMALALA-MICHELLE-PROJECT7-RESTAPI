package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	@NotEmpty(message = "Username is mandatory")
	@Size(max= 255, message= "Username cannot be longer than 255 characters")
	@Column(name = "username", nullable = false, length= 255)
	private String username;

	@NotNull(message = "Password is mandatory")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
	@Column(name = "password", nullable = false, length= 255)
	private String password;

	private String fullname;
	private String role;
	
	//GETTERS & SETTERS 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public User() {
		super();
	}
	public User(Integer id, @NotEmpty(message = "Username is mandatory") String username,
			@NotNull(message = "Password is mandatory") @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.") String password,
			String fullname, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}
	public User(@NotEmpty(message = "Username is mandatory") String username,
			@NotNull(message = "Password is mandatory") @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.") String password,
			String fullname, String role) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}
	
}