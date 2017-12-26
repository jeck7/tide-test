package com.example.tidetest.controller;

import com.example.tidetest.model.User;
import com.example.tidetest.service.UserService;
import com.example.tidetest.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/features")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    String port;

    @Autowired
    UserService userService;

    /**
     * Get All Users in DB
     *
     * @return List of all Users
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            logger.info("Users does not exists");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        logger.info("Found " + users.size() + " Users");
        logger.info(Arrays.toString(users.toArray()));
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * Get the User by ID
     *
     * @param userId of User
     * @return User
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        logger.info("Fetching User with id {}", userId);
        User user = userService.getById(userId);
        if (user == null) {
            logger.info("User with id {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("User with id " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    /**
     * Add new User
     *
     * @param user
     * @return added User
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userService.save(user);
        logger.info("Added:: " + user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    /**
     * Update existing User
     *
     * @param user
     * @return void
     */
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        User existingUsr = userService.getById(user.getId());
        if (existingUsr == null) {
            logger.info("User with id " + user.getId() + " does not exists");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            userService.save(user);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    /**
     * Delete User
     *
     * @param id of User to delete
     * @return void
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            logger.info("User with id " + id + " does not exists");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            userService.delete(id);
            logger.debug("User with id " + id + " deleted");
            return new ResponseEntity<Void>(HttpStatus.GONE);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        logger.info("Access /");
        return "Hi!";
    }

    @RequestMapping(value = "/ribbon")
    public String hello() {
        return "Hello from a service running at port: " + port + "!";
    }

}
