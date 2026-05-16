package com.richard.todo.features.user.domain.exceptions;

import com.richard.todo.features.user.domain.enums.UserErrorCode;

public class EmailAlreadyExistsException extends UserDomainException {

	public EmailAlreadyExistsException(String email) {
		super(UserErrorCode.EMAIL_ALREADY_EXISTS, "Email already registered: " + email);
	}
}
