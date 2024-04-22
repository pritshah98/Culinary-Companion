package com.ps.culinarycompanion.user;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of all users from the user service and returns it as a response entity.
     *
     * @return         	A response entity containing the list of users and an HTTP status of OK.
     */
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Retrieves a user by their email address and returns it as a response entity.
     *
     * @param  email  the email address of the user to retrieve
     * @return        a response entity containing the user and an HTTP status of OK
     */
    @GetMapping("/api/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    /**
     * Creates a new user by calling the userService to create the user and returns a ResponseEntity with the created user and HTTP status of CREATED.
     *
     * @param  user  the user object to be created
     * @return       a response entity containing the created user and an HTTP status of CREATED
     */
    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Updates a user with the given email.
     *
     * @param  user   the user object containing the updated information
     * @param  email  the email of the user to be updated
     * @return        a response entity with no content and a status of OK
     */
    @PutMapping("/api/users/{email}")
    @Transactional
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, @PathVariable String email) {
        userService.updateUser(user, email);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a user with the given email.
     *
     * @param  email  the email of the user to be deleted
     * @return        a response entity with no content and a status of OK
     */
    @DeleteMapping("/api/users/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

}
