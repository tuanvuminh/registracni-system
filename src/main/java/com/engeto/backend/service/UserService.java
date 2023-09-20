package com.engeto.backend.service;

import com.engeto.backend.exception.UserException;
import com.engeto.backend.model.UserDetailed;
import com.engeto.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Založení nového uživatele
    @Override
    public Integer createUser(UserDetailed userDetailed) {
        return jdbcTemplate.update("INSERT INTO Persons (name, surname, personID, uuid) VALUES(?,?,?,UUID())",
                userDetailed.getName(), userDetailed.getSurname(), userDetailed.getPersonID());
    }

    // Informace o uživateli
    @Override
    public User getUsersNonDetailedInfo(Long id) {
        String sql = "SELECT id, name, surname FROM Persons WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname")
                );
                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getUserDetailedInfo(Long id) {
        String sql = "SELECT id, name, surname, personID, uuid FROM Persons WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> {
                UserDetailed userDetailed = new UserDetailed();
                userDetailed.setId(resultSet.getLong("id"));
                userDetailed.setName(resultSet.getString("name"));
                userDetailed.setSurname(resultSet.getString("surname"));
                try {
                    userDetailed.setPersonID(resultSet.getString("personID"));
                } catch (UserException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                userDetailed.setUuid(resultSet.getString("uuid").getBytes());
                return userDetailed;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Informace o všech uživatelích
    @Override
    public List<User> getUsersDetailedInfoList() {
        String sql = "SELECT id, name, surname, personID, uuid FROM Persons";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            UserDetailed userDetailed = new UserDetailed();
            userDetailed.setId(resultSet.getLong("id"));
            userDetailed.setName(resultSet.getString("name"));
            userDetailed.setSurname(resultSet.getString("surname"));
            try {
                userDetailed.setPersonID(resultSet.getString("personID"));
            } catch (UserException e) {
                System.err.println(e.getLocalizedMessage());
            }
            userDetailed.setUuid(resultSet.getString("uuid").getBytes());
            return userDetailed;
        });
    }

    @Override
    public List<User> getUsersNonDetailedInfoList() {
        String sql = "SELECT id, name, surname FROM Persons";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                    User user = new User(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname")
                    );
                    return user;
                }
        );
    }

    // Upravení informací o uživateli
    @Override
    public Integer updateUser(User userToUpdate) {
        if (userToUpdate != null) {
            return jdbcTemplate.update("UPDATE Persons SET name = ?, surname = ? WHERE id = ?",
                    userToUpdate.getName(), userToUpdate.getSurname(), userToUpdate.getId());
        } else {
            return null;
        }
    }

    // Smazání uživatele
    @Override
    public Integer deleteUserByID(Long id) {
        return jdbcTemplate.update("DELETE FROM Persons WHERE id=?", id);
    }
}

