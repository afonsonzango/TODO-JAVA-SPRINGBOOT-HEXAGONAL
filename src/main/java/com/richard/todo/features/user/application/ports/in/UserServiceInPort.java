package com.richard.todo.features.user.application.ports.in;

import com.richard.todo.features.user.application.dtos.CreateUserRequestDTO;
import com.richard.todo.features.user.application.dtos.UpdateUserRequestDTO;
import com.richard.todo.features.user.application.dtos.UserResponseDTO;
import java.util.List;
import java.util.UUID;

public interface UserServiceInPort {

	UserResponseDTO create(CreateUserRequestDTO request);

	UserResponseDTO findById(UUID id);

	List<UserResponseDTO> findAll();

	UserResponseDTO update(UUID id, UpdateUserRequestDTO request);

	void delete(UUID id);
}
