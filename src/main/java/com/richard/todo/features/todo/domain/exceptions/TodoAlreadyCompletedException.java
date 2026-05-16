package com.richard.todo.features.todo.domain.exceptions;

import com.richard.todo.features.todo.domain.enums.TodoErrorCode;

import java.util.UUID;

public class TodoAlreadyCompletedException extends TodoDomainException {

	public TodoAlreadyCompletedException(UUID id) {
		super(TodoErrorCode.TODO_ALREADY_COMPLETED, "Todo is already completed: " + id);
	}
}
