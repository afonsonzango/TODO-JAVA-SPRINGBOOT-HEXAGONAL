package com.richard.todo.features.todo.application.ports.out;

import com.richard.todo.features.todo.domain.models.TodoModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepositoryOutPort {

	TodoModel save(TodoModel todo);

	Optional<TodoModel> findById(UUID id);

	List<TodoModel> findByUserId(UUID userId);

	List<TodoModel> findAll();

	void deleteById(UUID id);
}
