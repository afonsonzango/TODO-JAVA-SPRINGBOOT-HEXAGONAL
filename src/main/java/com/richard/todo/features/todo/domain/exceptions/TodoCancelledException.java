package com.richard.todo.features.todo.domain.exceptions;

import com.richard.todo.features.todo.domain.enums.TodoErrorCode;

import java.util.UUID;

public class TodoCancelledException extends TodoDomainException {

	public TodoCancelledException(UUID id) {
		super(TodoErrorCode.TODO_CANCELLED, "Todo is cancelled and cannot be modified: " + id);
	}
}
