package com.richard.todo.features.user.domain.exceptions;

import com.richard.todo.features.user.domain.enums.UserErrorCode;

public abstract class UserDomainException extends RuntimeException {

	private final UserErrorCode code;

	protected UserDomainException(UserErrorCode code, String message) {
		super(message);
		this.code = code;
	}

	public UserErrorCode getCode() {
		return code;
	}
}
