package com.richard.todo.features.todo.domain.mappers;

import com.richard.todo.features.todo.application.dtos.TodoResponseDTO;
import com.richard.todo.features.todo.domain.models.TodoModel;
import com.richard.todo.features.todo.infrastructure.entity.TodoEntity;
import com.richard.todo.features.user.domain.mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TodoMapper {
	@Mapping(target = "user", ignore = true)
	TodoModel toModel(TodoEntity entity);

	TodoEntity toEntity(TodoModel model);

	TodoResponseDTO toResponseDTO(TodoModel model);
}