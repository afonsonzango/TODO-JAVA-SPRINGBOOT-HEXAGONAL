package com.richard.todo.features.todo.application.services;

import com.richard.todo.features.todo.application.dtos.CreateTodoRequestDTO;
import com.richard.todo.features.todo.application.dtos.TodoResponseDTO;
import com.richard.todo.features.todo.application.dtos.UpdateTodoRequestDTO;
import com.richard.todo.features.todo.application.ports.in.TodoServiceInPort;
import com.richard.todo.features.todo.application.ports.out.TodoRepositoryOutPort;
import com.richard.todo.features.todo.domain.enums.TodoPriorityEnum;
import com.richard.todo.features.todo.domain.enums.TodoStatusEnum;
import com.richard.todo.features.todo.domain.exceptions.TodoAlreadyCompletedException;
import com.richard.todo.features.todo.domain.exceptions.TodoCancelledException;
import com.richard.todo.features.todo.domain.exceptions.TodoNotFoundException;
import com.richard.todo.features.todo.domain.mappers.TodoMapper;
import com.richard.todo.features.todo.domain.models.TodoModel;
import com.richard.todo.features.user.application.ports.out.UserRepositoryOutPort;
import com.richard.todo.features.user.domain.exceptions.UserNotFoundException;
import com.richard.todo.features.user.domain.models.UserModel;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoService implements TodoServiceInPort {

	private final TodoRepositoryOutPort todoRepository;
	private final UserRepositoryOutPort userRepository;
	private final TodoMapper mapper;

	public TodoService(
			TodoRepositoryOutPort todoRepository,
			UserRepositoryOutPort userRepository,
			TodoMapper mapper
	) {
		this.todoRepository = todoRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	@Override
	public TodoResponseDTO create(CreateTodoRequestDTO request) {
		UserModel user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new UserNotFoundException(request.getUserId()));

		TodoModel todo = new TodoModel();
		todo.setUser(user);
		todo.setTitle(request.getTitle().trim());
		todo.setDescription(request.getDescription());
		todo.setStatus(TodoStatusEnum.PENDING);
		todo.setPriority(request.getPriority() != null ? request.getPriority() : TodoPriorityEnum.MEDIUM);
		todo.setDueDate(request.getDueDate());

		return mapper.toResponseDTO(todoRepository.save(todo));
	}

	@Override
	public TodoResponseDTO findById(UUID id) {
		TodoModel todo = todoRepository.findById(id)
				.orElseThrow(() -> new TodoNotFoundException(id));
		return mapper.toResponseDTO(todo);
	}

	@Override
	public List<TodoResponseDTO> findByUserId(UUID userId) {
		if (userRepository.findById(userId).isEmpty()) {
			throw new UserNotFoundException(userId);
		}
		return todoRepository.findByUserId(userId).stream().map(mapper::toResponseDTO).toList();
	}

	@Override
	public List<TodoResponseDTO> findAll() {
		return todoRepository.findAll().stream().map(mapper::toResponseDTO).toList();
	}

	@Override
	public TodoResponseDTO update(UUID id, UpdateTodoRequestDTO request) {
		TodoModel todo = todoRepository.findById(id)
				.orElseThrow(() -> new TodoNotFoundException(id));

		ensureModifiable(todo);

		if (request.getTitle() != null) {
			todo.setTitle(request.getTitle().trim());
		}
		if (request.getDescription() != null) {
			todo.setDescription(request.getDescription());
		}
		if (request.getStatus() != null) {
			todo.setStatus(request.getStatus());
		}
		if (request.getPriority() != null) {
			todo.setPriority(request.getPriority());
		}
		if (request.getDueDate() != null) {
			todo.setDueDate(request.getDueDate());
		}

		return mapper.toResponseDTO(todoRepository.save(todo));
	}

	@Override
	public void delete(UUID id) {
		if (todoRepository.findById(id).isEmpty()) {
			throw new TodoNotFoundException(id);
		}
		todoRepository.deleteById(id);
	}

	private void ensureModifiable(TodoModel todo) {
		if (todo.getStatus() == TodoStatusEnum.COMPLETED) {
			throw new TodoAlreadyCompletedException(todo.getId());
		}
		if (todo.getStatus() == TodoStatusEnum.CANCELLED) {
			throw new TodoCancelledException(todo.getId());
		}
	}
}
