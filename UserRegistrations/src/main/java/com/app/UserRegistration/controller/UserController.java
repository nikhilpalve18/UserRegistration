package com.app.UserRegistration.controller;

import com.app.UserRegistration.model.UserRequestDTO;
import com.app.UserRegistration.model.UserResponseDTO;
import com.app.UserRegistration.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Operation(summary = "Save new user", description = "This api helps in creating a new user")
    @PostMapping("/save-user")
    public CompletableFuture<ResponseEntity<String>> saveUser(@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        return this.userService.createUser(userRequestDTO)
                .thenApply(result -> ResponseEntity.ok("User created successfully"));
    }

    @Operation(summary = "Get user Information", description = "This api helps in getting user information with username")
    @GetMapping("/{username}")
    public CompletableFuture<UserResponseDTO> getUserInformation(@PathVariable("username") String username) throws Exception {
        return this.userService.getUser(username);
    }

    @Operation(summary = "Delete user", description = "This API helps in deleting the user by userId")
    @DeleteMapping("/delete-user/{userId}")
    public CompletableFuture<ResponseEntity<String>> deleteUser(@PathVariable("userId") Long userId) throws Exception {
        return this.userService.deleteUser(userId)
                .thenApply(result -> ResponseEntity.ok("User deleted successfully"));
    }

    @Operation(summary = "Retrieve all the users", description = "This api helps in retrieving all the users from database")
    @GetMapping("/all-users")
    public CompletableFuture<List<UserResponseDTO>> getAllUsers() {
        return this.userService.getAllUsers();
    }

}
