package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.exception.UserAlreadyPresentException;
import com.app.UserRegistration.exception.UserNotFoundException;
import com.app.UserRegistration.model.UserDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    /* attributes */
    private final UserRepository userRepository;

    /* constructor */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /* Creating a new user */
    @Override
    public void createUser(UserDTO userDTO) throws Exception {
        User user = User.builder().firstName(userDTO.getFirstName()).
                lastName(userDTO.getLastName()).password(userDTO.getPassword()).
                username(userDTO.getUsername()).mobileNumber(userDTO.getMobileNumber()).build();

        /* check for duplicate users */
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local != null) {
            throw new UserAlreadyPresentException("User is already present");
        }
        else{
            this.userRepository.save(user);
        }
    }

    /* get the details of user by its username */
    @Override
    public UserDTO getUser(String username) throws Exception {
        User user = this.userRepository.findByUsername(username);

        if(user == null){
            throw new UserNotFoundException("User not found with username " + username);
        }

        return UserDTO.builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).username(user.getUsername()).mobileNumber(user.getMobileNumber()).build();
    }

    /* delete user by its Id */
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    /* get all the registered users */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
