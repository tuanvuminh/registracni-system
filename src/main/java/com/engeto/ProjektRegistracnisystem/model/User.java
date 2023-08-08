package com.engeto.ProjektRegistracnisystem.model;

import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;

public class User {
    private Long ID;
    private String name;
    private String surname;
    private String personID;
    private byte[] uuid;

    public User() {
    }

    public User(String name, String surname, String personID) {
        this.name = name;
        this.surname = surname;
        this.personID = personID;
    }

    public User(Long ID, String name, String surname) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        if (personID.length() == 12)
            this.personID = personID;
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

    public String nonDetailedInfo() {
        return "{ID: " + ID +
                ", name: " + name +
                ", surname: " + surname + " }";
    }

    public String detailedInfo() {
        return "{ID: " + ID +
                ", name: " + name +
                ", surname: " + surname +
                ", personID: " + personID +
                ", uuid: " + uuid + " }";
    }
}
