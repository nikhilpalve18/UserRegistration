package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.exception.UserAlreadyPresentException;
import com.app.UserRegistration.exception.UserNotFoundException;
import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void createUser(UserRequestDTO userRequestDTO) throws Exception {
        System.out.println(userRequestDTO.getUser());
        User user = User.builder().firstName(userRequestDTO.getUser().getFirstName()).
                lastName(userRequestDTO.getUser().getLastName()).password(userRequestDTO.getUser().getPassword()).
                username(userRequestDTO.getUser().getUsername()).contacts(userRequestDTO.getUser().getContacts()).build();

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
    public UserResponseDTO getUser(String username) throws Exception {
        User user = this.userRepository.findByUsername(username);

        if(user == null)
            throw new UserNotFoundException("User not found with username " + username);

        return UserResponseDTO.builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).username(user.getUsername()).contacts(user.getContacts()).build();
    }

    /* delete user by its Id */
    @Override
    public void deleteUser(Long userId) throws Exception{
        if(this.userRepository.existsById(userId)){
            this.userRepository.deleteById(userId);
        }
        else{
            throw new UserNotFoundException("User not found with id " + userId);
        }
    }

    /* get all the registered users */
    @Override
    public List<UserResponseDTO> getAllUsers() {
            List<User> users = this.userRepository.findAll();

            return users.stream()
                    .map(user -> UserResponseDTO.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .username(user.getUsername())
                            .contacts(user.getContacts())
                            .build())
                    .collect(Collectors.toList());
    }


}
