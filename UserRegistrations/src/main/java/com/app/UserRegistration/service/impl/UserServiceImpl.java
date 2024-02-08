package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.exception.UserAlreadyPresentException;
import com.app.UserRegistration.exception.UserNotFoundException;
import com.app.UserRegistration.exception.ValidationException;
import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import com.app.UserRegistration.validation.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /* attributes */
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    /* constructor */
    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }


    /* Creating a new user */
    @Override
    @Async
    public CompletableFuture<Void> createUser(UserRequestDTO userRequestDTO) throws ValidationException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        userValidator.validate(userRequestDTO);

        try {
            User user = User.builder().firstName(userRequestDTO.getUser().getFirstName()).
                lastName(userRequestDTO.getUser().getLastName()).password(userRequestDTO.getUser().getPassword()).
                username(userRequestDTO.getUser().getUsername()).contacts(userRequestDTO.getUser().getContacts()).build();

            User local = this.userRepository.findByUsername(user.getUsername());
            if(local != null) {
                System.out.println("Exception thrown");;
                throw new UserAlreadyPresentException("User is already present");
            }
            this.userRepository.save(user);
            log.info("User saved successfully by " + Thread.currentThread().getName());
            future.complete(null);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        return future;
    }



    /* get the details of user by its username */
    @Override
    @Async
    public CompletableFuture<UserResponseDTO> getUser(String username) throws Exception {
        User user = this.userRepository.findByUsername(username);

        if(user == null)
            throw new UserNotFoundException("User not found with username " + username);

        UserResponseDTO userResponseDTO = UserResponseDTO.builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).username(user.getUsername()).contacts(user.getContacts()).build();
        log.info(Thread.currentThread().getName());
        return CompletableFuture.completedFuture(userResponseDTO);
    }

    /* delete user by its Id */
    @Override
    @Async
    public CompletableFuture<Void> deleteUser(Long userId) throws Exception{
        CompletableFuture<Void> future = new CompletableFuture<>();

        try {
            if(this.userRepository.existsById(userId)){
                this.userRepository.deleteById(userId);
                log.info(Thread.currentThread().getName());
            }
            else{
                throw new UserNotFoundException("Cannot delete, User not found with id " + userId);
            }
            log.info("User deleted successfully by " + Thread.currentThread().getName());
            future.complete(null);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    /* get all the registered users */
    @Override
    @Async
    public CompletableFuture<List<UserResponseDTO>> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        log.info("Total no of users: " + users.size() + " " + Thread.currentThread().getName());
        List<UserResponseDTO> responseDTOList = users.stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .contacts(user.getContacts())
                        .build())
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(responseDTOList);
    }
}
