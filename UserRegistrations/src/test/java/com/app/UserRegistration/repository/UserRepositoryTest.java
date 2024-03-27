package com.app.UserRegistration.repository;

import com.app.UserRegistration.entity.Contact;
import com.app.UserRegistration.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    User user;

    @BeforeEach
    void setUp() {
        user = buildUserObject();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
        userRepository.deleteAll();
    }

    private static User buildUserObject() {
        Contact contact = Contact.builder().email("nikhilpalve18@gmail.com").
                mobile("9087676565").build();

        // Create a list of contacts
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);

        return User.builder().firstName("Nikhil").lastName("Palve").username("nikhilpalve18").
                password("Nikhil@12345").contacts(contacts).build();
    }

    @Test
    void testFindByUsername_FOUND() {
        User userResult = userRepository.findByUsername(user.getUsername());
        assertThat(user).isEqualTo(userResult);
    }

    @Test
    void testFindByUsername_NotFOUND() {
        String username = "nikhilpalve1889";
        User expected = userRepository.findByUsername(username);
        assertThat(expected).isNull();
    }

}