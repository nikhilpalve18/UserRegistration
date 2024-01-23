package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.model.UserDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    /* attributes */
    private final UserRepository userRepository;

    /* constructor */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* methods
     Creating a new user */
    @Override
    public void createUser(UserDTO userDTO) throws Exception {
        User user = User.builder().firstName(userDTO.getFirstName()).
                lastName(userDTO.getLastName()).password(userDTO.getPassword()).
                username(userDTO.getUsername()).mobileNumber(userDTO.getMobileNumber()).build();

        /* check for duplicate users */
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local != null) {
            System.out.println("User is already present");
            throw new Exception("User already present");
        }
        else{
            this.userRepository.save(user);
        }
    }

    /* get the details of user by its username */
    @Override
    public UserDTO getUser(String username) {
        User user = this.userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();

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
