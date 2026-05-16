package com.richard.todo.features.todo.application.dtos;

import com.richard.todo.features.todo.domain.enums.TodoPriorityEnum;
import com.richard.todo.features.todo.domain.enums.TodoStatusEnum;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public class UpdateTodoRequestDTO {

	@Size(min = 1, max = 150, message = "Title cannot be blank and must be at most 150 characters")
	private String title;

	@Size(max = 5000, message = "Description must be at most 5000 characters")
	private String description;

	private TodoStatusEnum status;

	private TodoPriorityEnum priority;

	private Instant dueDate;

	public UpdateTodoRequestDTO() {
	}

	public UpdateTodoRequestDTO(String title, String description, TodoStatusEnum status, TodoPriorityEnum priority, Instant dueDate) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
	}

	@AssertTrue(message = "At least one field must be provided for update")
	public boolean isAtLeastOneFieldPresent() {
		return title != null
				|| description != null
				|| status != null
				|| priority != null
				|| dueDate != null;
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
}
