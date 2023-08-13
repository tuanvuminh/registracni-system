package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.exceptions.UserException;
import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.model.UserNonDetailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class UserService implements UserRepository, RowMapper<User> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }

    // Založení nového uživatele
    @Override
    public int createUser(User user) {
        return jdbcTemplate.update("INSERT INTO Persons (name, surname, personID, uuid) VALUES(?,?,?,UUID_TO_BIN(UUID()))",
                user.getName(), user.getSurname(), user.getPersonID());
    }

    // Informace o uživateli
    @Override
    public Object getUserDetails(Long ID, boolean detail) {
        String sql;
        if (detail) {
            sql = "SELECT ID, name, surname, personID, uuid FROM Persons WHERE ID = ?";
        } else {
            sql = "SELECT ID, name, surname FROM Persons WHERE ID = ?";
        }

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{ID}, (resultSet, rowNum) -> {
                if (detail) {
                    User user = new User();
                    user.setID(resultSet.getLong("ID"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    try {
                        user.setPersonID(resultSet.getString("personID"));
                    } catch (UserException e) {
                        System.err.println(e.getLocalizedMessage());
                    }
                    user.setUuid(resultSet.getString("uuid").getBytes());
                    return user;
                } else {
                    UserNonDetailed user = new UserNonDetailed(
                            resultSet.getLong("ID"),
                            resultSet.getString("name"),
                            resultSet.getString("surname")
                    );
                    return user;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Informace o všech uživatelích
    @Override
    public List<Object> getUsersList(boolean detail) {
        String sql;
        if (detail) {
            sql = "SELECT ID, name, surname, personID, uuid FROM Persons";
        } else {
            sql = "SELECT ID, name, surname FROM Persons";
        }

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            if (detail) {
                User user = new User();
                user.setID(resultSet.getLong("ID"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                try {
                    user.setPersonID(resultSet.getString("personID"));
                } catch (UserException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                user.setUuid(resultSet.getString("uuid").getBytes());
                return user;
            } else {
                UserNonDetailed user = new UserNonDetailed(
                        resultSet.getLong("ID"),
                        resultSet.getString("name"),
                        resultSet.getString("surname")
                );
                return user;
            }
        });
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

