package com.example.tidetest.controller;

import com.example.tidetest.model.User;
import com.example.tidetest.repository.UserRepository;
import com.example.tidetest.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/features")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        logger.info("Fetching User with id {}", userId);
        User user = userRepository.findOne(userId);
        if (user == null) {
            logger.error("User with id {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("User with id " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    public String home() {
        logger.info("Access /");
        return "Hi!";
    }

}
