package com.richard.todo.features.todo.adapters.rest.exceptions;

import com.richard.todo.features.todo.domain.exceptions.TodoCancelledException;
import com.richard.todo.root.shared.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TodoCancelledExceptionMapper {

	@ExceptionHandler(TodoCancelledException.class)
	public ResponseEntity<ErrorResponseDTO> toResponse(TodoCancelledException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponseDTO(exception.getCode().name(), exception.getMessage()));
	}
}
