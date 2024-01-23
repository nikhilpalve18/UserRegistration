package com.app.UserRegistration.controller;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.model.UserDTO;
import com.app.UserRegistration.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    /* Attributes */
    private final UserService userService;

    /* Constructor */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* Methods
     This api saves the user information. */
    @PostMapping("/save-user")
    public void saveUser(@RequestBody UserDTO userDTO) throws Exception {
        this.userService.createUser(userDTO);
    }

    /* This api retrieves the information of a user. */
    @GetMapping("/{username}")
    public UserDTO getUserInformation(@PathVariable("username") String username){
        return this.userService.getUser(username);
    }

    /* This api deletes the user */
    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        this.userService.deleteUser(userId);
    }

    /* This api retrieves all the users from DB. */
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
