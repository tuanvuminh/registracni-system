package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createUser(User user) {
        return jdbcTemplate.update("INSERT INTO Persons (name, surname, personID, uuid) VALUES(?,?,?,UUID_TO_BIN(UUID()))",
                user.getName(), user.getSurname(), user.getPersonID());
    }

    @Override
    public User getUsersDetailedInfo(Long ID) {
        String sql = "SELECT * FROM Persons WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ID}, userRowMapper);
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPersonID(String.valueOf(rs.getInt("personID")));
        return user;
    };


    @Override
    public User getUsersUnDetailedInfo(Long ID) {
        String sql = "SELECT ID, name, Surname FROM Persons WHERE ID=?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{ID}, new BeanPropertyRowMapper<>(User.class));
        return user;
    }

    @Override
    public List<User> getAllUsersDetailedInfo() {
        String sql = "SELECT * from Persons";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public List<User> getAllUsersUnDetailedInfo() {
        String sql = "SELECT ID, name, surname from Persons";
        List<User> people = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
        return new ArrayList<>(people);
    }

    @Override
    public int updateUser(Object updatedUser) {
        if (updatedUser instanceof User user) {
            return jdbcTemplate.update("UPDATE Persons SET name = ?, surname = ? WHERE ID = ?",
                    user.getName(), user.getSurname(), user.getID());
        } else {
            return Integer.parseInt(null);
        }
    }

    @Override
    public int deleteUserByID(Long ID) {
        return jdbcTemplate.update("DELETE FROM Persons WHERE ID=?", ID);
    }

}

