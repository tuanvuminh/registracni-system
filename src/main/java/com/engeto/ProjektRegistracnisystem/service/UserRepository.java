package com.engeto.ProjektRegistracnisystem.service;

import com.engeto.ProjektRegistracnisystem.model.User;
import java.util.List;
public interface UserRepository {

    int createUser(User user);

    Object getUserDetails(Long ID, boolean detail);

    List<Object> getUsersList(boolean detail);

    int updateUser(Object updatedUser);

    int deleteUserByID(Long ID);
}


