package com.richard.todo.features.todo.application.dtos;

import com.richard.todo.features.todo.domain.enums.TodoPriorityEnum;
import com.richard.todo.features.todo.domain.enums.TodoStatusEnum;
import java.time.Instant;
import java.util.UUID;

public class TodoResponseDTO {
	private UUID id;
	private UUID userId;
	private String title;
	private String description;
	private TodoStatusEnum status;
	private TodoPriorityEnum priority;
	private Instant dueDate;
	private Instant createdAt;
	private Instant updatedAt;

	public TodoResponseDTO() {
	}

	public TodoResponseDTO(
			UUID id,
			UUID userId,
			String title,
			String description,
			TodoStatusEnum status,
			TodoPriorityEnum priority,
			Instant dueDate,
			Instant createdAt,
			Instant updatedAt
	) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TodoStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TodoStatusEnum status) {
		this.status = status;
	}

	public TodoPriorityEnum getPriority() {
		return priority;
	}

	public void setPriority(TodoPriorityEnum priority) {
		this.priority = priority;
	}

	public Instant getDueDate() {
		return dueDate;
	}

	public void setDueDate(Instant dueDate) {
		this.dueDate = dueDate;
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