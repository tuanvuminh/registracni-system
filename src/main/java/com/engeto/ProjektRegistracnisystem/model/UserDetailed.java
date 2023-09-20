package com.engeto.ProjektRegistracnisystem.model;

import com.engeto.ProjektRegistracnisystem.exception.UserException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class UserDetailed extends User {

    private String personID;
    private byte[] uuid;

    public UserDetailed() {
    }

    public UserDetailed(String name, String surname, String personID) throws UserException {
        super(name, surname);
        this.setPersonID(personID);
    }

    public UserDetailed(Long id, String name, String surname, String personID, byte[] uuid) throws UserException {
        super(id, name, surname);
        this.setPersonID(personID);
        this.uuid = uuid;
    }

    public String getPersonID() {
        return personID;
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

        String filePath = "data/personID.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }
}
