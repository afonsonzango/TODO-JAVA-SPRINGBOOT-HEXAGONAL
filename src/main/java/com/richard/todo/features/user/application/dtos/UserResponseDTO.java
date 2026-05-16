package com.richard.todo.features.user.application.dtos;

import java.time.Instant;
import java.util.UUID;

public class UserResponseDTO {

	private UUID id;
	private String name;
	private String email;
	private Instant createdAt;
	private Instant updatedAt;

	public UserResponseDTO() {
	}

	public UserResponseDTO(UUID id, String name, String email, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
