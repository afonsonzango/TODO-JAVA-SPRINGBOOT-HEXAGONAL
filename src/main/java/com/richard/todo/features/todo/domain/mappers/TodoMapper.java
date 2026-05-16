package com.richard.todo.features.todo.domain.mappers;

import com.richard.todo.features.todo.application.dtos.TodoResponseDTO;
import com.richard.todo.features.todo.domain.models.TodoModel;
import com.richard.todo.features.todo.infrastructure.entity.TodoEntity;
import com.richard.todo.features.user.domain.mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TodoMapper {
	TodoModel toModel(TodoEntity entity);

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	TodoEntity toEntity(TodoModel model);

	@Mapping(source = "user.id", target = "userId")
	TodoResponseDTO toResponseDTO(TodoModel model);
}