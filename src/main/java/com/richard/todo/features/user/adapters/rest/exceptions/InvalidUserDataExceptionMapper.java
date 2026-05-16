package com.richard.todo.features.user.adapters.rest.exceptions;

import com.richard.todo.features.user.domain.exceptions.InvalidUserDataException;
import com.richard.todo.root.shared.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidUserDataExceptionMapper {
	@ExceptionHandler(InvalidUserDataException.class)
	public ResponseEntity<ErrorResponseDTO> toResponse(InvalidUserDataException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponseDTO(exception.getCode().name(), exception.getMessage()));
	}
}
