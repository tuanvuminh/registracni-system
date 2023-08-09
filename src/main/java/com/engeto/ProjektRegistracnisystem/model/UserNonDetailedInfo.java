package com.engeto.ProjektRegistracnisystem.model;

public class UserNonDetailedInfo {
    private Long ID;
    private String name;
    private String surname;

    public UserNonDetailedInfo(Long ID, String name, String surname) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
    }

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
