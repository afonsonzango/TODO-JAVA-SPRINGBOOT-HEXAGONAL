package com.richard.todo.features.user.domain.exceptions;

import com.richard.todo.features.user.domain.enums.UserErrorCode;

public class InvalidUserDataException extends UserDomainException {

	public InvalidUserDataException(String message) {
		super(UserErrorCode.INVALID_USER_DATA, message);
	}
}
