package com.richard.todo.features.user.adapters.rest.exceptions;

import com.richard.todo.features.user.domain.exceptions.UserNotFoundException;
import com.richard.todo.root.shared.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundExceptionMapper {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> toResponse(UserNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponseDTO(exception.getCode().name(), exception.getMessage()));
	}
}
