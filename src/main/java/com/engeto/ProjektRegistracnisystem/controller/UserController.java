package com.engeto.ProjektRegistracnisystem.controller;

import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.model.UserNonDetailed;
import com.engeto.ProjektRegistracnisystem.service.UserService;
import com.engeto.ProjektRegistracnisystem.settings.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    public static final String NOT_FOUND = "User was not found in database.";
    public static final ApiResponse CREATED = new ApiResponse(true,
            "User was added successfully.");
    public static final ApiResponse NOT_CREATED = new ApiResponse(false,
            "User could not be added, because this person ID is already in database.");
    public static final ApiResponse UPDATED = new ApiResponse(true,
            "User was updated successfully.");
    public static final ApiResponse NOT_UPDATED = new ApiResponse(false,
            "User could not be updated.");
    public static final ApiResponse DELETED = new ApiResponse(true,
            "User was deleted successfully.");
    public static final ApiResponse NOT_DELETED = new ApiResponse(false,
            "User could not be deleted.");
    public static final ApiResponse INVALID_ID = new ApiResponse(false,
            "User could not be added, because this person ID is invalid.");

    // Založení nového uživatele POST api/v1/user
    @PostMapping("/user")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody User user) {
        try {
            userService.createUser(new User(user.getName(), user.getSurname(), user.getPersonID()));
            return ResponseEntity.status(HttpStatus.CREATED).body(CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(NOT_CREATED);
        }
    }

    /*  Informace o uživateli GET api/v1/users/{ID}
        {id: string, name: string, surname: string}
        Informace o uživateli GET api/v1/users/{ID}?detail=true
        {id: string, name: string, surname: string, personID: string , uuid: string} */
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id, @RequestParam(required = false) boolean detail) {
        if (detail) {
            User user = userService.getUserDetailedInfo(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
            }
        } else {
            UserNonDetailed userNonDetailed = userService.getUsersNonDetailedInfo(id);
            if (userNonDetailed != null) {
                return ResponseEntity.ok(userNonDetailed);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
            }
        }
    }

    /* Informace o všech uživatelích GET api/v1/users
       {id: string, name: string, surname: string}
       Informace o všech uživatelích GET api/v1/users?detail=true
       {id: string, name: string, surname: string, personID: string , uuid: string} */
    @GetMapping("/users")
    public ResponseEntity<List<Object>> getUsersList(@RequestParam(required = false) boolean detail) {
        List<UserNonDetailed> usersNonDetailedInfoList = userService.getUsersNonDetailedInfoList();
        List<User> usersDetailedInfoList = userService.getUsersDetailedInfoList();
        if (!detail && !usersNonDetailedInfoList.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonList(usersNonDetailedInfoList));
        } else if (detail && !usersDetailedInfoList.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonList(usersDetailedInfoList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Upravení informací o uživateli PUT api/v1/users/{ID}
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody User user) {
        User userToUpdate = userService.getUserDetailedInfo(id);
        if (userToUpdate != null) {
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userService.updateUser(userToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(UPDATED);
        } else if (userToUpdate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_UPDATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(NOT_UPDATED);
        }
    }

    // Smazání uživatele DELETE api/v1/users/{ID}
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id) {
        Integer result = userService.deleteUserByID(id);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_DELETED);
        } else if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(DELETED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(NOT_DELETED);
        }
    }
}
