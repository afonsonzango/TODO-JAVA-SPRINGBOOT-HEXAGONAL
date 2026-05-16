package com.richard.todo.features.todo.adapters.rest.exceptions;

import com.richard.todo.features.todo.domain.exceptions.InvalidTodoDataException;
import com.richard.todo.root.shared.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidTodoDataExceptionMapper {

	@ExceptionHandler(InvalidTodoDataException.class)
	public ResponseEntity<ErrorResponseDTO> toResponse(InvalidTodoDataException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponseDTO(exception.getCode().name(), exception.getMessage()));
	}
}
