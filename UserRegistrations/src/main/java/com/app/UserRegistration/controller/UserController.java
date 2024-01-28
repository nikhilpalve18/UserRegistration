package com.app.UserRegistration.controller;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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


    @Operation(summary = "Register the new user", description = "This api helps in registering a new user")
    @PostMapping("/save-user")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserRequestDTO userRequestDTO) throws Exception {
            this.userService.createUser(userRequestDTO);
            return ResponseEntity.ok("User created successfully");
    }

    @Operation(summary = "Get user Information", description = "This api helps in getting user information with username")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInformation(@PathVariable("username") String username) throws Exception {
        return ResponseEntity.ok(this.userService.getUser(username));
    }

    @Operation(summary = "Delete user", description = "This api helps in deleting the user by userId")
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) throws Exception {
            this.userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(summary = "Retrieve all the users", description = "This api helps in retrieving all the users from database")
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
}
