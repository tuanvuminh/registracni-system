package com.engeto.ProjektRegistracnisystem.model;

import com.engeto.ProjektRegistracnisystem.exception.UserException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class User {

    private Long id;
    private String name;
    private String surname;
    private String personID;
    private byte[] uuid;

    public User() {
    }

    public User(String name, String surname, String personID) throws UserException {
        this.name = name;
        this.surname = surname;
        this.setPersonID(personID);
    }

    public User(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public void setPersonID(String personID) throws UserException {
        if (!isValidPersonID(personID)) {
            throw new UserException("Person ID is not valid.");
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
