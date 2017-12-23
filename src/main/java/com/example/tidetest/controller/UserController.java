package com.example.tidetest.controller;

import com.example.tidetest.model.User;
import com.example.tidetest.repository.UserRepository;
import com.example.tidetest.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    String port;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/features/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/features/user", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }


    @RequestMapping(value = "/features/user/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        logger.info("Access /");
        return "Hi!";
    }

    @RequestMapping(value = "/features/ribbon")
    public String hello() {
        return "Hello from a service running at port: " + port + "!";
    }

}
