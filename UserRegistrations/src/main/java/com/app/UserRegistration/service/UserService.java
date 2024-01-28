package com.app.UserRegistration.service;

import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;

import java.util.List;

public interface UserService {
    public void createUser(UserRequestDTO userRequestDTO) throws Exception;
    public UserResponseDTO getUser(String username) throws Exception;

    public void deleteUser(Long userId) throws Exception;

    public List<UserResponseDTO> getAllUsers();
}
