package com.app.UserRegistration.service;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.model.UserDTO;

import java.util.List;

public interface UserService {
    public void createUser(UserDTO userDTO) throws Exception;
    public UserDTO getUser(String username) throws Exception;

    public void deleteUser(Long userId) throws Exception;

    public List<User> getAllUsers();
}
