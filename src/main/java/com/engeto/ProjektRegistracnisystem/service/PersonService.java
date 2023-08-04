package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements PersonRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createPerson(Person person) {
        return jdbcTemplate.update("INSERT INTO Persons (name, surname, personID, uuid) VALUES(?,?,?,UUID_TO_BIN(UUID()))",
                person.getName(), person.getSurname(), person.getPersonID());
    }

    @Override
    public Person getPersonsDetailedInfo(Long ID) {
        String sql = "SELECT * FROM Persons WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ID}, userRowMapper);
    }

    private final RowMapper<Person> userRowMapper = (rs, rowNum) -> {
        Person person = new Person();
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));
        person.setPersonID(String.valueOf(rs.getInt("personID")));
        return person;
    };


    @Override
    public Person getPersonsUnDetailedInfo(Long ID) {
        String sql = "SELECT ID, name, Surname FROM Persons WHERE ID=?";
        Person person = jdbcTemplate.queryForObject(sql, new Object[]{ID}, new BeanPropertyRowMapper<>(Person.class));
        return person;
    }

    @Override
    public List<Person> getAllPersonsDetailedInfo() {
        String sql = "SELECT * from Persons";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public List<Person> getAllPersonsUnDetailedInfo() {
        String sql = "SELECT ID, name, surname from Persons";
        List<Person> people = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Person.class));
        return new ArrayList<>(people);
    }

    @Override
    public int updatePerson(Object updatedPerson) {
        if (updatedPerson instanceof Person person) {
            return jdbcTemplate.update("UPDATE Persons SET name = ?, surname = ? WHERE ID = ?",
                    person.getName(), person.getSurname(), person.getID());
        } else {
            return Integer.parseInt(null);
        }
    }

    @Override
    public int deletePersonByID(Long ID) {
        return jdbcTemplate.update("DELETE FROM Persons WHERE ID=?", ID);
    }

}

