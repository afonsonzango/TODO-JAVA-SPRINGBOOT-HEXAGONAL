package com.richard.todo.features.user.application.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequestDTO {

	@Size(min = 1, max = 100, message = "Name cannot be blank and must be at most 100 characters")
	private String name;

	@Email(message = "Email must be valid")
	@Size(min = 1, max = 150, message = "Email cannot be blank and must be at most 150 characters")
	private String email;

	@Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
	private String password;

	public UpdateUserRequestDTO() {
	}

	public UpdateUserRequestDTO(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	@AssertTrue(message = "At least one field must be provided for update")
	public boolean isAtLeastOneFieldPresent() {
		return name != null || email != null || password != null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
