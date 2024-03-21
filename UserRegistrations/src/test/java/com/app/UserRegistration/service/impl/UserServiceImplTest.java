package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.entity.Contact;
import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import com.app.UserRegistration.service.UserServiceImpl;
import com.app.UserRegistration.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void getAllUsersTestReturnEmptyList() throws Exception {

        CompletableFuture<List<UserResponseDTO>> userFuture = userService.getAllUsers();
        List<UserResponseDTO> actualUsers;
        actualUsers = userFuture.get();

        assertEquals(0, actualUsers.size());
    }

    @Test
    void getAllUsersTest() throws Exception {
        UserRequestDTO userRequestDTO = createValidUserRequestDTO();
        User expectedUser = createUserFromRequestDTO(userRequestDTO);

        userService.createUser(userRequestDTO);

        CompletableFuture<List<UserResponseDTO>> userFuture = userService.getAllUsers();
        List<UserResponseDTO> actualUsers = userFuture.get();
        System.out.println(actualUsers);

        assertThat(1).isEqualTo(actualUsers.size());
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

    @Test
    public void deleteUserTest() throws Exception {
        Long userId = 1L; // Assuming the user ID is retrieved or known

        UserRequestDTO userRequestDTO = createValidUserRequestDTO();
        User expectedUser = createUserFromRequestDTO(userRequestDTO);
        userService.createUser(userRequestDTO);

        userService.deleteUser(userId);

        User deletedUser = userRepository.findById(userId).orElse(null);
        assertNull(deletedUser, "User should be deleted");
    }

    @Test
    public void getUserTest() throws Exception {
        String username = "nikhilpalve18786";

        UserRequestDTO userRequestDTO = createValidUserRequestDTO();
        User expectedUser = createUserFromRequestDTO(userRequestDTO);
        userService.createUser(userRequestDTO);


        CompletableFuture<UserResponseDTO> userFuture = userService.getUser(username);
        UserResponseDTO actualUser = userFuture.get();

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }


}