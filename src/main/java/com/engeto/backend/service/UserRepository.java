package com.engeto.backend.service;

import com.engeto.backend.model.User;
import com.engeto.backend.model.UserDetailed;

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


