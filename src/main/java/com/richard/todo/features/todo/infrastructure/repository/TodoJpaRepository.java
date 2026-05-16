package com.richard.todo.features.todo.infrastructure.repository;

import com.richard.todo.features.todo.infrastructure.entity.TodoEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, UUID> {
	List<TodoEntity> findByUser_Id(UUID userId);
}
