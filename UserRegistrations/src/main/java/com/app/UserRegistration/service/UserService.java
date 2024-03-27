package com.app.UserRegistration.service;

import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    public CompletableFuture<Void> createUser(UserRequestDTO userRequestDTO) throws Exception;
    public CompletableFuture<UserResponseDTO> getUser(String username) throws Exception;
    public CompletableFuture<Void> deleteUser(Long userId) throws Exception;
    public CompletableFuture<List<UserResponseDTO>> getAllUsers();
}
