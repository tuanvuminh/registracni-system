package com.engeto.ProjektRegistracnisystem.controllers;

import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String mainPage() {
        return "Welcome to Genesis Resources!";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersInfo() {
        List<User> allUsers = userService.getAllUsersDetailedInfo();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/user/{ID}")
    public ResponseEntity<User> getUserById(@PathVariable Long ID) {
        User user = userService.getUsersDetailedInfo(ID);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        try {
            userService.createUser(new User(user.getName(), user.getSurname(), user.getPersonID()));
            return new ResponseEntity<>("User was added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User could not be added", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{ID}")
    public ResponseEntity<String> updateUser(@PathVariable Long ID, @RequestBody User user) {
        Object updatedUser = userService.getUsersDetailedInfo(ID);
        if (updatedUser instanceof User userToUpdate) {
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userService.updateUser(userToUpdate);
            return new ResponseEntity<>("User was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find user with ID = " + ID, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{ID}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long ID) {
        try {
            int result = userService.deleteUserByID(ID);
            if (result == 0) {
                return new ResponseEntity<>("User with ID " + ID + " could not be found.", HttpStatus.OK);
            }
            return new ResponseEntity<>("User was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User with ID " + ID + " could not be deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
