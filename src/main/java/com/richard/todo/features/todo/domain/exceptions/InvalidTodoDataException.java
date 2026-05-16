package com.richard.todo.features.todo.domain.exceptions;

import com.richard.todo.features.todo.domain.enums.TodoErrorCode;

public class InvalidTodoDataException extends TodoDomainException {
	public InvalidTodoDataException(String message) {
		super(TodoErrorCode.INVALID_TODO_DATA, message);
	}
}