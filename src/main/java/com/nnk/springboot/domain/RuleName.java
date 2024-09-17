package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "rulename")
public class RuleName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty(message = "Name is mandatory")
	@Column(name = "name", nullable = false, length= 255)
	private String name;

	@NotEmpty(message = "Description is mandatory")
	@Column(name = "description", nullable = false, length= 255)
	private String description;

	private String json;

	private String template;

	private String sqlstr;

	private String sqlpart;
	
	
	//GETTERS & SETTERS

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}

	public String getSqlpart() {
		return sqlpart;
	}

	public void setSqlpart(String sqlpart) {
		this.sqlpart = sqlpart;
	}



	public RuleName(Integer id, @NotEmpty(message = "Name is mandatory") String name,
			@NotEmpty(message = "Description is mandatory") String description, String json, String template,
			String sqlstr, String sqlpart) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlstr = sqlstr;
		this.sqlpart = sqlpart;
	}

	
	
	public RuleName(@NotEmpty(message = "Name is mandatory") String name,
			@NotEmpty(message = "Description is mandatory") String description, String json, String template,
			String sqlstr, String sqlpart) {
		super();
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlstr = sqlstr;
		this.sqlpart = sqlpart;
	}

	public RuleName() {
		super();
	}



}
