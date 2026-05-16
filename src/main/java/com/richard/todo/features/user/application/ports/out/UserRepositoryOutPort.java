package com.richard.todo.features.user.application.ports.out;

import com.richard.todo.features.user.domain.models.UserModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryOutPort {

	UserModel save(UserModel user);

	Optional<UserModel> findById(UUID id);

	Optional<UserModel> findByEmail(String email);

	List<UserModel> findAll();

	void deleteById(UUID id);

	boolean existsByEmail(String email);
}
