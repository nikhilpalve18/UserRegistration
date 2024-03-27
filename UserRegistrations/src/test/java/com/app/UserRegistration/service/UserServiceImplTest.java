package com.app.UserRegistration.service;


import com.app.UserRegistration.entity.Contact;
import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.exception.UserNotFoundException;
import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUser_Success() throws Exception {
        String username = "nikhilpalve18";
        UserRequestDTO userRequestDTO = createValidUserRequestDTO();
        User user = createUserFromRequestDTO(userRequestDTO);
        when(userRepository.findByUsername(username)).thenReturn(user);

        CompletableFuture<UserResponseDTO> userFuture = userService.getUser(username);
        UserResponseDTO result = userFuture.get();

        verify(userRepository).findByUsername(username);

        assertEquals(username, result.getUsername());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getContacts(), result.getContacts());
    }

    @Test
    public void getUser_UserNotFound() throws Exception {
        String username = "nikhil18";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getUser(username));
        verify(userRepository).findByUsername(username);
    }

    @Test
    public void deleteUser_Success() throws Exception {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        CompletableFuture<Void> deleteFuture = userService.deleteUser(userId);
        deleteFuture.get();

        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void deleteUser_UserNotFound() throws Exception {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        CompletableFuture<Void> deleteFuture = userService.deleteUser(userId);
        assertThrows(Exception.class, deleteFuture::get);

        verify(userRepository).existsById(userId);
    }

    @Test
    public void getAllUsers_Success() throws Exception {
        UserRequestDTO userRequestDTO = createValidUserRequestDTO();

        List<User> users = new ArrayList<>();
        users.add(createUserFromRequestDTO(userRequestDTO));

        when(userRepository.findAll()).thenReturn(users);

        CompletableFuture<List<UserResponseDTO>> userFuture = userService.getAllUsers();
        List<UserResponseDTO> resultList = userFuture.get();

        verify(userRepository).findAll();
        assertThat(users.size()).isEqualTo(resultList.size());

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

        User user = User.builder().username("nikhilpalve18")
                .firstName("Nikhil")
                .lastName("Palve")
                .password("Nikhil@12543")
                .contacts(contacts).build();

        return UserRequestDTO.builder().user(user).build();
    }
}