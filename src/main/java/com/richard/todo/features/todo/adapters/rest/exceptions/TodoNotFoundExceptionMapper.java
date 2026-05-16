package com.richard.todo.features.todo.adapters.rest.exceptions;

import com.richard.todo.features.todo.domain.exceptions.TodoNotFoundException;
import com.richard.todo.root.shared.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TodoNotFoundExceptionMapper {

	@ExceptionHandler(TodoNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> toResponse(TodoNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponseDTO(exception.getCode().name(), exception.getMessage()));
	}
}
