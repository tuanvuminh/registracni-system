package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import java.util.List;

public interface UserRepository {

    int createUser(User user);

    User getUsersDetailedInfo(Long ID);

    List<User> getAllUsersDetailedInfo();

    public int updateUser(Object updatedUser);

    int deleteUserByID(Long ID);
}


