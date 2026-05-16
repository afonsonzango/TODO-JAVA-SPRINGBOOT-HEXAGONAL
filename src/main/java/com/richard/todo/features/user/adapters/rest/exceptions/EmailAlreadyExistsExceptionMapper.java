package com.richard.todo.features.user.adapters.rest.exceptions;

import com.richard.todo.features.user.domain.exceptions.EmailAlreadyExistsException;
import com.richard.todo.root.shared.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailAlreadyExistsExceptionMapper {
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDTO> toResponse(EmailAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponseDTO(exception.getCode().name(), exception.getMessage()));
	}
}
