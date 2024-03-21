package com.app.UserRegistration.repository;

import com.app.UserRegistration.entity.Contact;
import com.app.UserRegistration.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void itShouldfindByUsername() {
        // given
        User user = buildUserObject();
        userRepository.save(user);

        // when
        User userResult = userRepository.findByUsername("nikhilpalve18");

        assertThat(user).isEqualTo(userResult);
    }

    @Test
    void itShouldCheckUsernameDoesNotExists() {
        // given
        String username = "nikhilpalve18";
        // User user = buildUserObject();
        // userRepository.save(user);

        // when
        User expected = userRepository.findByUsername(username);

        assertThat(expected).isNull();
    }
    private static User buildUserObject(){
        Contact contact = Contact.builder()
                .email("nikhilpalve18@gmail.com")
                .mobile("9087676565")
                .build();

        // Create a list of contacts
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);

        // Build the user object using the builder pattern
        return User.builder()
                .firstName("Nikhil")
                .lastName("Palve")
                .username("nikhilpalve18")
                .password("Nikhil@12345")
                .contacts(contacts)
                .build();
    }

}