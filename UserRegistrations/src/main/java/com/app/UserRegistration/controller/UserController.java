package com.app.UserRegistration.controller;

import com.app.UserRegistration.model.Users;
import com.app.UserRegistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public Users saveUser(@RequestBody Users users) throws Exception {
        System.out.println("Hello my friend");
        System.out.println(users.getFirstName());
        return this.userService.createUser(users);
    }
}
