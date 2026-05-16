package com.richard.todo.features.user.adapters.rest.controllers;

import com.richard.todo.features.user.application.dtos.CreateUserRequestDTO;
import com.richard.todo.features.user.application.dtos.UpdateUserRequestDTO;
import com.richard.todo.features.user.application.dtos.UserResponseDTO;
import com.richard.todo.features.user.application.ports.in.UserServiceInPort;
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
@RequestMapping("/api/users")
public class UserController {

	private final UserServiceInPort userService;

	public UserController(UserServiceInPort userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDTO create(@Valid @RequestBody CreateUserRequestDTO request) {
		return userService.create(request);
	}

	@GetMapping("/{id}")
	public UserResponseDTO findById(@PathVariable UUID id) {
		return userService.findById(id);
	}

	@GetMapping
	public List<UserResponseDTO> findAll() {
		return userService.findAll();
	}

	@PutMapping("/{id}")
	public UserResponseDTO update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequestDTO request) {
		return userService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable UUID id) {
		userService.delete(id);
	}
}
