package com.richard.todo.features.user.domain.exceptions;

import com.richard.todo.features.user.domain.enums.UserErrorCode;

import java.util.UUID;

public class UserNotFoundException extends UserDomainException {

	public UserNotFoundException(UUID id) {
		super(UserErrorCode.USER_NOT_FOUND, "User not found: " + id);
	}
}
