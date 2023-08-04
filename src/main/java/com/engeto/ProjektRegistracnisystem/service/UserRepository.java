package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import java.util.List;

public interface UserRepository {

    int createUser(User user);

    User getUsersDetailedInfos(Long ID);

    User getUsersUnDetailedInfos(Long ID);

    List<User> getAllUsersDetailedInfos();

    List<User> getAllUsersUnDetailedInfos();

    String updateUser(Long ID, String name, String surname);

    int deleteUserByID(Long ID);
}


