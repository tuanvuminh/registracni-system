package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import java.util.List;

public interface UserRepository {

    int createUser(User user);

    User getUsersDetailedInfos(Long ID);

    User getUsersUnDetailedInfos(Long ID);

    List<User> getAllUsersDetailedInfos();

    List<User> getAllUsersUnDetailedInfos();

    public int updateUser(Object user);

    int deleteUserByID(Long ID);
}


