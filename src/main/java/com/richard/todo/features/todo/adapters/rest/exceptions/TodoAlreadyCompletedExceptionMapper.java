package com.richard.todo.features.todo.adapters.rest.exceptions;

import com.richard.todo.features.todo.domain.exceptions.TodoAlreadyCompletedException;
import com.richard.todo.root.shared.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TodoAlreadyCompletedExceptionMapper {

	@ExceptionHandler(TodoAlreadyCompletedException.class)
	public ResponseEntity<ErrorResponseDTO> toResponse(TodoAlreadyCompletedException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponseDTO(exception.getCode().name(), exception.getMessage()));
	}
}
