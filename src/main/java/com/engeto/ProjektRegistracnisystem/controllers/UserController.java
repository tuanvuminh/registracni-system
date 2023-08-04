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
    public String test() {
        return "Hello!";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersInfo() {
        List<User> users = userService.getAllUsersDetailedInfos();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{ID}")
    public ResponseEntity<User> getUserById(@PathVariable Long ID) {
        User user = userService.getUsersDetailedInfos(ID);
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
            return new ResponseEntity<>("User was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User could not be created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{ID}")
    public ResponseEntity<String> updateUsersInfo(@PathVariable Long ID,
                                                  @RequestParam String newName,
                                                  @RequestParam String newSurname) {
        String result = userService.updateUser(ID, newName, newSurname);
        if (result.startsWith("Updated")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{ID}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long ID) {
        try {
            int result = userService.deleteUserByID(ID);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find user with ID = " + ID, HttpStatus.OK);
            }
            return new ResponseEntity<>("User was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
