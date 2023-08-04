package com.engeto.ProjektRegistracnisystem.model;

import java.util.Arrays;

public class Person {
    private static int idCounter = 1;
    private Long ID = Long.valueOf(idCounter++);
    private String name;
    private String surname;
    private String personID;
    private byte[] uuid;

    public Person(String name, String surname, String personID) {
        this.name = name;
        this.surname = surname;
        this.personID = personID;
    }

    public Person() {
    }

    public boolean isPersonIDValid() {
        if (this.personID.length() == 12) return true;
        return false;
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

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personID='" + personID + '\'' +
                ", uuid=" + Arrays.toString(uuid) +
                '}';
    }
}
