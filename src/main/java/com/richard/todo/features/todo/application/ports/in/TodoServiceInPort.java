package com.richard.todo.features.todo.application.ports.in;

import com.richard.todo.features.todo.application.dtos.CreateTodoRequestDTO;
import com.richard.todo.features.todo.application.dtos.TodoResponseDTO;
import com.richard.todo.features.todo.application.dtos.UpdateTodoRequestDTO;
import java.util.List;
import java.util.UUID;

public interface TodoServiceInPort {
	TodoResponseDTO 				create(CreateTodoRequestDTO request);

	TodoResponseDTO 				findById(UUID id);

	List<TodoResponseDTO> 			findByUserId(UUID userId);

	List<TodoResponseDTO> 			findAll();

	TodoResponseDTO 				update(UUID id, UpdateTodoRequestDTO request);

	void 							delete(UUID id);
}
