package com.richard.todo.features.todo.domain.exceptions;

import com.richard.todo.features.todo.domain.enums.TodoErrorCode;

public abstract class TodoDomainException extends RuntimeException {

	private final TodoErrorCode code;

	protected TodoDomainException(TodoErrorCode code, String message) {
		super(message);
		this.code = code;
	}

	public TodoErrorCode getCode() {
		return code;
	}
}
