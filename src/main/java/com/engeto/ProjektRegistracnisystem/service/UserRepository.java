package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.model.UserDetailed;

import java.util.List;
public interface UserRepository {

    Integer createUser(UserDetailed userDetailed);

    User getUsersNonDetailedInfo(Long id);

    User getUserDetailedInfo(Long id);

    List<User> getUsersNonDetailedInfoList();

    List<User> getUsersDetailedInfoList();

    Integer updateUser(User userToUpdate);

    Integer deleteUserByID(Long id);
}


