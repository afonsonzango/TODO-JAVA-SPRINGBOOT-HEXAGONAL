package com.richard.todo.features.todo.infrastructure.entity;

import com.richard.todo.features.todo.domain.enums.TodoPriorityEnum;
import com.richard.todo.features.todo.domain.enums.TodoStatusEnum;
import com.richard.todo.features.user.infrastructure.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;

@Entity
@Table(name = "todos")
@EntityListeners(AuditingEntityListener.class)
public class TodoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(nullable = false, length = 150)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TodoStatusEnum status = TodoStatusEnum.PENDING;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private TodoPriorityEnum priority = TodoPriorityEnum.MEDIUM;

	@Column(name = "due_date")
	private Instant dueDate;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	public TodoEntity() {
	}

	public TodoEntity(UUID id, UserEntity user, String title, String description, TodoStatusEnum status, TodoPriorityEnum priority, Instant dueDate, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.user = user;
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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