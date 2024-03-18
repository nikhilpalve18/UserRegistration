package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.entity.Contact;
import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.exception.ValidationException;
import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import com.app.UserRegistration.service.UserServiceImpl;
import com.app.UserRegistration.validation.UserValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;
    protected UserValidator userValidator;



    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
        userService = new UserServiceImpl(userRepository, userValidator);
    }

    @Test
    void getAllUsers() throws ExecutionException, InterruptedException {
        // You can create sample users as well and save to database
        List<UserResponseDTO> expectedUsers = new ArrayList<>();

        CompletableFuture<List<UserResponseDTO>> userFuture = userService.getAllUsers();
        List<UserResponseDTO> actualUsers;
        actualUsers = userFuture.get();

        assertEquals(0, actualUsers.size());

    }

    @Test
    public void createUserTest() throws Exception {
        UserRequestDTO userRequestDTO = createValidUserRequestDTO();
        User expectedUser = createUserFromRequestDTO(userRequestDTO);

        userService.createUser(userRequestDTO);

        User actualUser = userRepository.findByUsername(expectedUser.getUsername());
        System.out.println(actualUser);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());


    }


    private User createUserFromRequestDTO(UserRequestDTO userRequestDTO) {
        return User.builder().username(userRequestDTO.getUser().getUsername())
                .password(userRequestDTO.getUser().getPassword())
                .firstName(userRequestDTO.getUser().getFirstName())
                .lastName(userRequestDTO.getUser().getLastName())
                .contacts(userRequestDTO.getUser().getContacts()).build();
    }


    private UserRequestDTO createValidUserRequestDTO() {
        Contact contact = Contact.builder()
                .email("nikhilpalve18@gmail.com")
                .mobile("9087876769")
                .build();

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);

        User user = User.builder().username("nikhilpalve18786")
                .firstName("Nikhil")
                .lastName("Palve")
                .password("Nikhil@12543")
                .contacts(contacts).build();

        return UserRequestDTO.builder().user(user).build();
    }
}