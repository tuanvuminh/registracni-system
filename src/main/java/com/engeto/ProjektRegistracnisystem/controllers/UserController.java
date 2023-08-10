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
            return ResponseEntity.status(HttpStatus.CREATED).body("User was added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be added, because personID is not valid.");
        }
    }

    /*  Informace o uživateli GET api/v1/user/{ID}
        {id: string, name: string, surname: string}
        Informace o uživateli GET api/v1/user/{ID}?detail=true
        {id: string, name: string, surname: string, personID: string , uuid: string} */
    @GetMapping("/user/{ID}")
    public ResponseEntity<Object> getUserById(@PathVariable Long ID, @RequestParam(required = false) boolean detail) {
        Object user = userService.getUserDetails(ID, detail);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + ID + " was not found.");
        }
    }

    /* Informace o všech uživatelích GET api/v1/users
       List <{id: string, name: string, surname: string}>
       Informace o všech uživatelích GET api/v1/users?detail=true
       {id: string, name: string, surname: string, personID: string , uuid: string} */
    @GetMapping("/users")
    public ResponseEntity<List<Object>> getUsersList(@RequestParam(required = false) boolean detail) {
        List<Object> userList = userService.getUsersList(detail);
        if (!userList.isEmpty()) {
            return ResponseEntity.ok(userList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Upravení informací o uživateli
    @PutMapping("/user/{ID}")
    public ResponseEntity<String> updateUser(@PathVariable Long ID, @RequestBody User user) {
        Object updatedUser = userService.getUserDetails(ID, true);
        if (updatedUser instanceof User userToUpdate) {
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userService.updateUser(userToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body("User was updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + ID + " was not found.");
        }
    }

    // Smazání uživatele DELETE api/v1/user/{ID}
    @DeleteMapping("/user/{ID}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long ID) {
        int result = userService.deleteUserByID(ID);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + ID + " was not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("User was deleted successfully.");
        }
    }

}
