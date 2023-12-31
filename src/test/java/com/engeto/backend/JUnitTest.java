package com.engeto.backend;

import com.engeto.backend.controller.UserController;
import com.engeto.backend.exception.UserException;
import com.engeto.backend.model.User;
import com.engeto.backend.model.UserDetailed;
import com.engeto.backend.service.UserService;
import com.engeto.backend.model.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

import static com.engeto.backend.controller.UserController.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@SpringBootTest
public class JUnitTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Přidání uživatele s validním personID.")
    public void addUserWithValidPersonID() throws UserException {
        UserDetailed user = new UserDetailed("Jake", "Sully", "jXa4g3H7oPq2");
        when(userService.createUser(any())).thenReturn(1);

        // Test
        ResponseEntity<ApiResponse> test = userController.addNewUser(user);

        // Ověření
        assertEquals(HttpStatus.CREATED, test.getStatusCode());
        assertEquals(CREATED, test.getBody());

        verify(userService, times(1)).createUser(any());
    }

    @Test
    @DisplayName("Získání informací o uživateli pomocí ID.")
    public void getUsersInfoByExistingID() throws UserException {
        UserDetailed user = new UserDetailed("Jake", "Sully", "jXa4g3H7oPq2");
        when(userService.getUserDetailedInfo(anyLong())).thenReturn(user);

        // Test
        ResponseEntity<?> test = userController.getUserById(1L, true);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals(user, test.getBody());

        verify(userService, times(1)).getUserDetailedInfo(anyLong());
    }

    @Test
    @DisplayName("Test získání informací uživatele pomocí neexistujícího ID.")
    public void getUsersInfoByNonExistingID() {
        when(userService.getUserDetailedInfo(anyLong())).thenReturn(null);

        // Test
        ResponseEntity<?> test = userController.getUserById(1L, true);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
        assertEquals(NOT_FOUND, test.getBody());

        verify(userService, times(1)).getUserDetailedInfo(anyLong());
    }

    @Test
    @DisplayName("Získání seznamu uživatelů, kteří jsou v databázi.")
    public void getUsersList() throws UserException {
        List<User> userList = new ArrayList<>();
        userList.add(new UserDetailed("Jake", "Sully", "jXa4g3H7oPq2"));
        when(userService.getUsersDetailedInfoList()).thenReturn(userList);

        // Test
        ResponseEntity<List<User>> test = userController.getUsersList(true);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals(userList, test.getBody());

        verify(userService, times(1)).getUsersDetailedInfoList();
    }

    @Test
    @DisplayName("Aktualizování informací uživatele, který je v databázi.")
    public void updateUser() throws UserException {
        User existingUser = new User(1l, "Jake", "Sully");
        when(userService.getUsersNonDetailedInfo(anyLong())).thenReturn(existingUser);

        // Test
        User updatedUserData = new User(1L,"John", "Smith");
        ResponseEntity<ApiResponse> test = userController.updateUser(1L, updatedUserData);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals(UPDATED, test.getBody());

        verify(userService, times(1)).getUsersNonDetailedInfo(anyLong());
        verify(userService, times(1)).updateUser(any());
    }


    @Test
    @DisplayName("Aktualizování informací uživatele, který není v databázi.")
    public void updateNonExistingUser() throws UserException {
        when(userService.getUsersNonDetailedInfo(anyLong())).thenReturn(null);

        // Test
        User updatedUserData = new User(50L,"John", "Smith");
        ResponseEntity<ApiResponse> test = userController.updateUser(50L, updatedUserData);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
        assertEquals(NOT_UPDATED, test.getBody());

        verify(userService, times(1)).getUsersNonDetailedInfo(anyLong());
        verify(userService, never()).updateUser(any());
    }

    @Test
    @DisplayName("Smazání uživatele pomocí ID.")
    public void deleteUserByID() {
        when(userService.deleteUserByID(anyLong())).thenReturn(1);

        // Test
        ResponseEntity<ApiResponse> test = userController.deleteUserById(1L);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals(DELETED, test.getBody());

        verify(userService, times(1)).deleteUserByID(anyLong());
    }

    @Test
    @DisplayName("Test smazání uživatele pomocí neexistujícího ID.")
    public void deleteUserByNonExistingID() {
        when(userService.deleteUserByID(anyLong())).thenReturn(0);

        // Test
        ResponseEntity<ApiResponse> test = userController.deleteUserById(1L);

        // Ověření
        assertEquals(INTERNAL_SERVER_ERROR, test.getStatusCode());
        assertEquals(NOT_DELETED, test.getBody());

        verify(userService, times(1)).deleteUserByID(anyLong());
    }
}
