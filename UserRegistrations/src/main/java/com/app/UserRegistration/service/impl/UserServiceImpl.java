package com.app.UserRegistration.service.impl;

import com.app.UserRegistration.entity.User;
import com.app.UserRegistration.model.UserDTO;
import com.app.UserRegistration.repository.UserRepository;
import com.app.UserRegistration.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    /* attributes */
    private final UserRepository userRepository;

    /* constructor */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* methods
     Creating a new user */
    @Override
    public void createUser(UserDTO userDTO) throws Exception {
        User user = User.builder().firstName(userDTO.getFirstName()).
                lastName(userDTO.getLastName()).password(userDTO.getPassword()).
                username(userDTO.getUsername()).mobileNumber(userDTO.getMobileNumber()).build();

        /* Server side validations*/
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getPassword());
        if(!matcher.matches())
            throw new Exception("Password must contain at least one special character, one small and capital alphabet and one number");

        regex = "^[0-9]{10}$";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(user.getMobileNumber());
        if(!matcher.matches())
            throw new Exception("Incorrect mobile number");

        if (user.getFirstName().isEmpty())
            throw new Exception("firstname cannot be empty");
        else if( user.getLastName().isEmpty())
            throw new Exception("lastname cannot be empty");
        else if (user.getUsername().isEmpty())
            throw new Exception("Username cannot be empty");


        /* check for duplicate users */
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local != null) {
            throw new Exception("User is already present");
        }
        else{
            this.userRepository.save(user);
        }
    }

    /* get the details of user by its username */
    @Override
    public UserDTO getUser(String username) throws Exception {
        User user = this.userRepository.findByUsername(username);
        if(user == null){
            throw new Exception("User not found");
        }



        UserDTO userDTO = new UserDTO();

        return UserDTO.builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName()).username(user.getUsername()).mobileNumber(user.getMobileNumber()).build();

    }

    /* delete user by its Id */
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    /* get all the registered users */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
