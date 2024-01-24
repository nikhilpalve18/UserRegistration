package com.app.UserRegistration.controller;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.model.UserDTO;
import com.app.UserRegistration.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
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
    @Operation(summary = "Register the new user", description = "This api helps in registering a new user")
    @PostMapping("/save-user")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws Exception {
        try {
            this.userService.createUser(userDTO);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* This api retrieves the information of a user. */
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInformation(@PathVariable("username") String username) throws Exception {
        try{
            return ResponseEntity.ok(this.userService.getUser(username));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
