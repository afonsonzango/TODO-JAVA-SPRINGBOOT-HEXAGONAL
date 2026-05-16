package com.richard.todo.features.todo.adapters.rest.controllers;

import com.richard.todo.features.todo.application.dtos.CreateTodoRequestDTO;
import com.richard.todo.features.todo.application.dtos.TodoResponseDTO;
import com.richard.todo.features.todo.application.dtos.UpdateTodoRequestDTO;
import com.richard.todo.features.todo.application.ports.in.TodoServiceInPort;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	private final TodoServiceInPort todoService;

	public TodoController(TodoServiceInPort todoService) {
		this.todoService = todoService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TodoResponseDTO create(@RequestBody CreateTodoRequestDTO request) {
		return todoService.create(request);
	}

	@GetMapping("/{id}")
	public TodoResponseDTO findById(@PathVariable UUID id) {
		return todoService.findById(id);
	}

	@GetMapping("/user/{userId}")
	public List<TodoResponseDTO> findByUserId(@PathVariable UUID userId) {
		return todoService.findByUserId(userId);
	}

	@GetMapping
	public List<TodoResponseDTO> findAll() {
		return todoService.findAll();
	}

	@PutMapping("/{id}")
	public TodoResponseDTO update(@PathVariable UUID id, @Valid @RequestBody UpdateTodoRequestDTO request) {
		return todoService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable UUID id) {
		todoService.delete(id);
	}
}
