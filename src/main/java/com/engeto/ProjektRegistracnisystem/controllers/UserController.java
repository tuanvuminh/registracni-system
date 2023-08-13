package com.engeto.ProjektRegistracnisystem.controllers;
import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.service.UserService;
import com.engeto.ProjektRegistracnisystem.settings.ApiResponse;
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
    public static final String NOT_FOUND = "User was not found in database.";
    public static ApiResponse CREATED = new ApiResponse(true,
            "User was added successfully.");
    public static ApiResponse NOT_CREATED = new ApiResponse(false,
            "User could not be added.");
    public static ApiResponse UPDATED = new ApiResponse(true,
            "User was updated successfully.");
    public static ApiResponse NOT_UPDATED = new ApiResponse(false,
            NOT_FOUND);
    public static ApiResponse DELETED = new ApiResponse(true,
            "User was deleted successfully.");
    public static ApiResponse NOT_DELETED = new ApiResponse(false,
            NOT_FOUND);

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
    }

    /* Informace o všech uživatelích GET api/v1/users
       {id: string, name: string, surname: string}
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

    // Upravení informací o uživateli PUT api/v1/user/{ID}
    @PutMapping("/user/{ID}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long ID, @RequestBody User user) {
        Object updatedUser = userService.getUserDetails(ID, true);
        if (updatedUser instanceof User userToUpdate) {
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userService.updateUser(userToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(UPDATED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_UPDATED);
        }
    }

    // Smazání uživatele DELETE api/v1/user/{ID}
    @DeleteMapping("/user/{ID}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long ID) {
        int result = userService.deleteUserByID(ID);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_DELETED);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(DELETED);
        }
    }
}
