package com.richard.todo.features.user.infrastructure.repository;

import com.richard.todo.features.user.application.ports.out.UserRepositoryOutPort;
import com.richard.todo.features.user.domain.mappers.UserMapper;
import com.richard.todo.features.user.domain.models.UserModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryAdapter implements UserRepositoryOutPort {

	private final UserJpaRepository userJpaRepository;
	private final UserMapper userMapper;

	public UserRepositoryAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
		this.userJpaRepository = userJpaRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserModel save(UserModel user) {
		return userMapper.toModel(userJpaRepository.save(userMapper.toEntity(user)));
	}

	@Override
	public Optional<UserModel> findById(UUID id) {
		return userJpaRepository.findById(id).map(userMapper::toModel);
	}

	@Override
	public Optional<UserModel> findByEmail(String email) {
		return userJpaRepository.findByEmail(email).map(userMapper::toModel);
	}

	@Override
	public List<UserModel> findAll() {
		return userJpaRepository.findAll().stream().map(userMapper::toModel).toList();
	}

	@Override
	public void deleteById(UUID id) {
		userJpaRepository.deleteById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userJpaRepository.existsByEmail(email);
	}
}
