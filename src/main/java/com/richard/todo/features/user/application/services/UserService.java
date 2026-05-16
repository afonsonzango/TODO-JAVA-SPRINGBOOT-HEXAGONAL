package com.richard.todo.features.user.application.services;

import com.richard.todo.features.user.application.dtos.CreateUserRequestDTO;
import com.richard.todo.features.user.application.dtos.UpdateUserRequestDTO;
import com.richard.todo.features.user.application.dtos.UserResponseDTO;
import com.richard.todo.features.user.application.ports.in.UserServiceInPort;
import com.richard.todo.features.user.application.ports.out.UserRepositoryOutPort;
import com.richard.todo.features.user.domain.exceptions.EmailAlreadyExistsException;
import com.richard.todo.features.user.domain.exceptions.UserNotFoundException;
import com.richard.todo.features.user.domain.mappers.UserMapper;
import com.richard.todo.features.user.domain.models.UserModel;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserServiceInPort {

	private final UserRepositoryOutPort userRepository;
	private final UserMapper mapper;

	public UserService(UserRepositoryOutPort userRepository, UserMapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	@Override
	public UserResponseDTO create(CreateUserRequestDTO request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException(request.getEmail());
		}

		UserModel user = new UserModel();
		user.setName(request.getName().trim());
		user.setEmail(request.getEmail().trim());
		user.setPassword(request.getPassword());

		return mapper.toResponseDTO(userRepository.save(user));
	}

	@Override
	public UserResponseDTO findById(UUID id) {
		UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return mapper.toResponseDTO(user);
	}

	@Override
	public List<UserResponseDTO> findAll() {
		return userRepository.findAll().stream().map(mapper::toResponseDTO).toList();
	}

	@Override
	public UserResponseDTO update(UUID id, UpdateUserRequestDTO request) {
		UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		if (request.getName() != null) {
			user.setName(request.getName().trim());
		}
		if (request.getEmail() != null) {
			if (!request.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
				throw new EmailAlreadyExistsException(request.getEmail());
			}
			user.setEmail(request.getEmail().trim());
		}
		if (request.getPassword() != null) {
			user.setPassword(request.getPassword());
		}

		return mapper.toResponseDTO(userRepository.save(user));
	}

	@Override
	public void delete(UUID id) {
		if (userRepository.findById(id).isEmpty()) {
			throw new UserNotFoundException(id);
		}
		userRepository.deleteById(id);
	}
}
