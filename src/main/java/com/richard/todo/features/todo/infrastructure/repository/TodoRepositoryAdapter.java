package com.richard.todo.features.todo.infrastructure.repository;

import com.richard.todo.features.todo.application.ports.out.TodoRepositoryOutPort;
import com.richard.todo.features.todo.domain.mappers.TodoMapper;
import com.richard.todo.features.todo.domain.models.TodoModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepositoryAdapter implements TodoRepositoryOutPort {

	private final TodoJpaRepository todoJpaRepository;
	private final TodoMapper todoMapper;

	public TodoRepositoryAdapter(TodoJpaRepository todoJpaRepository, TodoMapper todoMapper) {
		this.todoJpaRepository = todoJpaRepository;
		this.todoMapper = todoMapper;
	}

	@Override
	public TodoModel save(TodoModel todo) {
		return todoMapper.toModel(todoJpaRepository.save(todoMapper.toEntity(todo)));
	}

	@Override
	public Optional<TodoModel> findById(UUID id) {
		return todoJpaRepository.findById(id).map(todoMapper::toModel);
	}

	@Override
	public List<TodoModel> findByUserId(UUID userId) {
		return todoJpaRepository.findByUser_Id(userId).stream().map(todoMapper::toModel).toList();
	}

	@Override
	public List<TodoModel> findAll() {
		return todoJpaRepository.findAll().stream().map(todoMapper::toModel).toList();
	}

	@Override
	public void deleteById(UUID id) {
		todoJpaRepository.deleteById(id);
	}
}
