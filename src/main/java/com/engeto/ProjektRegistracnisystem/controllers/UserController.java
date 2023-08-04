package com.engeto.ProjektRegistracnisystem.controllers;

import com.engeto.ProjektRegistracnisystem.model.Person;
import com.engeto.ProjektRegistracnisystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private PersonService personService;

    @GetMapping
    public String mainPage() {
        return "Welcome to Genesis Resources!";
    }

    @GetMapping("/users")
    public ResponseEntity<List<Person>> getAllUsersInfo() {
        List<Person> people = personService.getAllUsersDetailedInfos();
        return ResponseEntity.ok(people);
    }

    @GetMapping("/user/{ID}")
    public ResponseEntity<Person> getUserById(@PathVariable Long ID) {
        Person person = personService.getUsersDetailedInfos(ID);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/person")
    public ResponseEntity<String> addNewUser(@RequestBody Person person) {
        try {
            personService.createUser(new Person(person.getName(), person.getSurname(), person.getPersonID()));
            return new ResponseEntity<>("Person was added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Person could not be added", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/person/{ID}")
    public ResponseEntity<String> updateUser(@PathVariable Long ID, @RequestBody Person person) {
        Object updatedUser = personService.getUsersDetailedInfos(ID);
        if (updatedUser instanceof Person personToUpdate) {
            personToUpdate.setName(person.getName());
            personToUpdate.setSurname(person.getSurname());
            personService.updateUser(personToUpdate);
            return new ResponseEntity<>("Person was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find person with ID = " + ID, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{ID}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long ID) {
        try {
            int result = personService.deleteUserByID(ID);
            if (result == 0) {
                return new ResponseEntity<>("Person with ID " + ID + " could not be found.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Person was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Person with ID " + ID + " could not be deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
