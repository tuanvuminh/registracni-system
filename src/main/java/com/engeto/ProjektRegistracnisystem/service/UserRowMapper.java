package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));
        person.setPersonID(String.valueOf(rs.getInt("personID")));
        return person;
    }
}
