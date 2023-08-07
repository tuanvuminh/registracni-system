package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setID(rs.getLong("ID"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPersonID(rs.getString("personID"));
        user.setUuid(rs.getBytes("uuid"));
        return user;
    };

    // Založení nového uživatele
    @Override
    public int createUser(User user) {
        return jdbcTemplate.update("INSERT INTO Persons (name, surname, personID, uuid) VALUES(?,?,?,UUID_TO_BIN(UUID()))",
                user.getName(), user.getSurname(), user.getPersonID());
    }

    // Informace o uživateli
    @Override
    public User getUsersDetailedInfo(Long ID) {
        String sql = "SELECT * FROM Persons WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ID}, userRowMapper);
    }

    // Informace o všech uživatelích
    @Override
    public List<User> getAllUsersDetailedInfo() {
        String sql = "SELECT * from Persons";
        return jdbcTemplate.query(sql,new Object[]{}, userRowMapper);
    }

    // Upravení informací o uživateli
    @Override
    public int updateUser(Object updatedUser) {
        if (updatedUser instanceof User user) {
            return jdbcTemplate.update("UPDATE Persons SET name = ?, surname = ? WHERE ID = ?",
                    user.getName(), user.getSurname(), user.getID());
        } else {
            return Integer.parseInt(null);
        }
    }

    // Smazání uživatele
    @Override
    public int deleteUserByID(Long ID) {
        return jdbcTemplate.update("DELETE FROM Persons WHERE ID=?", ID);
    }

}

