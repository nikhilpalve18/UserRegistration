package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.model.Users;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Users createUser(Users user) throws Exception {
        return this.userRepository.save(user);
    }
}
