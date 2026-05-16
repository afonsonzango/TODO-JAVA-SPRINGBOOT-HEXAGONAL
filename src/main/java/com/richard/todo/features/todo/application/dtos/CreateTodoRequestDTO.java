package com.richard.todo.features.todo.application.dtos;

import com.richard.todo.features.todo.domain.enums.TodoPriorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

public class CreateTodoRequestDTO {

	@NotNull(message = "User ID is required")
	private UUID userId;

	@NotBlank(message = "Title is required")
	@Size(max = 150, message = "Title must be at most 150 characters")
	private String title;

	@Size(max = 5000, message = "Description must be at most 5000 characters")
	private String description;

	private TodoPriorityEnum priority;

	private Instant dueDate;

	public CreateTodoRequestDTO() {
	}

	public CreateTodoRequestDTO(UUID userId, String title, String description, TodoPriorityEnum priority, Instant dueDate) {
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate;
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
}
