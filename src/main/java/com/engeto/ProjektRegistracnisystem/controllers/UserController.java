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

    // Založení nového uživatele
    @PostMapping("/user")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        try {
            userService.createUser(new User(user.getName(), user.getSurname(), user.getPersonID()));
            return new ResponseEntity<>("User was added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User could not be added", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Informace o uživateli GET api/v1/user/{ID}
    // {id: string, name: string, surname: string }
    // {id: string, name: string, surname: string, personID: string , uuid: string  }
    @GetMapping("/user/{ID}")
    public ResponseEntity<String> getUserById(@PathVariable Long ID, @RequestParam(required = false) boolean detail) {
        User user = userService.getUsersDetailedInfo(ID);
        StringBuilder resultTrue = new StringBuilder();
        resultTrue.append("{id: ").append(user.getID()).append(", name: ").append(user.getName()).append(", surname: ").
                append(user.getSurname()).append("}");
        if (detail) {
            return ResponseEntity.ok(user.toString());
        }
        return ResponseEntity.ok(resultTrue.toString());
    }


    // Informace o všech uživatelích GET api/v1/users
    // List <{id: string, name: string, surname: string }>
    @GetMapping("/users")
    public ResponseEntity<String> getAllUsersInfo() {
        List<User> allUsers = userService.getAllUsersDetailedInfo();
        StringBuilder result = new StringBuilder();
        for (User user : allUsers) {
            result.append("List <{id: ").append(user.getID()).append
                    (", name: ").append(user.getName()).append
                    (", surname: ").append(user.getSurname()).append("}>").append("\n");
        }
        return ResponseEntity.ok(result.toString());
    }

    // Upravení informací o uživateli
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

    // Smazání uživatele DELETE api/v1/user/{ID}
    @DeleteMapping("/user/{ID}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long ID) {
        try {
            int result = userService.deleteUserByID(ID);
            if (result == 0) {
                return new ResponseEntity<>("User with ID " + ID + " could not be found.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("User was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User with ID " + ID + " could not be deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
