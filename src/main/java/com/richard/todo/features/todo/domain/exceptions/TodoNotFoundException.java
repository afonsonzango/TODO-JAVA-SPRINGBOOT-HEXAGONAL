package com.richard.todo.features.todo.domain.exceptions;

import com.richard.todo.features.todo.domain.enums.TodoErrorCode;

import java.util.UUID;

public class TodoNotFoundException extends TodoDomainException {

	public TodoNotFoundException(UUID id) {
		super(TodoErrorCode.TODO_NOT_FOUND, "Todo not found: " + id);
	}
}
