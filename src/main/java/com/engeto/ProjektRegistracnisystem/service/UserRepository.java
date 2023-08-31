package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.model.UserNonDetailed;

import java.util.List;
public interface UserRepository {

    Integer createUser(User user);

    UserNonDetailed getUsersNonDetailedInfo(Long id);

    User getUserDetailedInfo(Long id);

    List<UserNonDetailed> getUsersNonDetailedInfoList();

    List<User> getUsersDetailedInfoList();

    Integer updateUser(User userToUpdate);

    Integer deleteUserByID(Long id);
}


