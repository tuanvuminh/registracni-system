package com.engeto.ProjektRegistracnisystem.model;

import com.engeto.ProjektRegistracnisystem.exceptions.UserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Long ID;
    private String name;
    private String surname;
    private String personID;
    private byte[] uuid;

    // Konstruktory
    public User() {
    }

    public User(String name, String surname, String personID) throws UserException {
        this.name = name;
        this.surname = surname;
        this.setPersonID(personID);
    }

    public User(Long ID, String name, String surname) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
    }

    // Ověření zda je personID validní a zda obsahuje 12 znaků.
    public void setPersonID(String personID) throws UserException {
        if (!isValidPersonID(personID)) {
            throw new UserException("User could not be added, because personID is not valid.");
        }
        this.personID = personID;
    }

    private boolean isValidPersonID(String personID) {
        if (personID.length() != 12) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("personID.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(personID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Gettery a settery
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getPersonID() {
        return personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }

}
