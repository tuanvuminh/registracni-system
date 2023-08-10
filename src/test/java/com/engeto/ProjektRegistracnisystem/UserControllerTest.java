package com.engeto.ProjektRegistracnisystem;
import com.engeto.ProjektRegistracnisystem.controllers.UserController;
import com.engeto.ProjektRegistracnisystem.exceptions.UserException;
import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Přidání uživatele s validním personID.")
    public void addUserWithValidPersonID() throws UserException {
        User user = new User("Jake", "Sully", "jXa4g3H7oPq2");
        when(userService.createUser(any())).thenReturn(1);

        // Test
        ResponseEntity<String> test = userController.addNewUser(user);

        // Ověření
        assertEquals(HttpStatus.CREATED, test.getStatusCode());
        assertEquals("User was added successfully.", test.getBody());

        verify(userService, times(1)).createUser(any());
    }

    @Test
    @DisplayName("Získání informací o uživateli pomocí ID.")
    public void getUsersInfoByExistingID() throws UserException {
        User user = new User("Jake", "Sully", "jXa4g3H7oPq2");
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(user);

        // Test
        ResponseEntity<Object> test = userController.getUserById(1L, true);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals(user, test.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
    }

    @Test
    @DisplayName("Test získání informací uživatele pomocí neexistujícího ID.")
    public void getUsersInfoByNonExistingID() {
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(null);

        // Test
        ResponseEntity<Object> test = userController.getUserById(1L, true);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
        assertEquals("User with ID 1 was not found.", test.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
    }

    @Test
    @DisplayName("Získání seznamu uživatelů, které jsou v databázi.")
    public void getUsersList() throws UserException {
        List<Object> userList = new ArrayList<>();
        userList.add(new User("Jake", "Sully", "jXa4g3H7oPq2"));
        when(userService.getUsersList(anyBoolean())).thenReturn(userList);

        // Test
        ResponseEntity<List<Object>> test = userController.getUsersList(true);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals(userList, test.getBody());

        verify(userService, times(1)).getUsersList(anyBoolean());
    }

    @Test
    @DisplayName("Aktualizování informací uživatele, který je v databázi.")
    public void updateUser() throws UserException {
        User existingUser = new User(1l, "Jake", "Sully");
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(existingUser);

        // Test
        User updatedUserData = new User(1L,"John", "Smith");
        ResponseEntity<String> test = userController.updateUser(1L, updatedUserData);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals("User was updated successfully.", test.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
        verify(userService, times(1)).updateUser(any());
    }

    @Test
    @DisplayName("Aktualizování informací uživatele, který není v databázi.")
    public void updateNonExistingUser() throws UserException {
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(null);

        // Test
        User updatedUserData = new User(50L,"John", "Smith");
        ResponseEntity<String> test = userController.updateUser(50L, updatedUserData);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
        assertEquals("User with ID 50 was not found.", test.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
        verify(userService, never()).updateUser(any());
    }

    @Test
    @DisplayName("Smazání uživatele pomocí ID.")
    public void deleteUserByID() {
        when(userService.deleteUserByID(anyLong())).thenReturn(1);

        // Test
        ResponseEntity<String> test = userController.deleteUserById(1L);

        // Ověření
        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals("User was deleted successfully.", test.getBody());

        verify(userService, times(1)).deleteUserByID(anyLong());
    }

    @Test
    @DisplayName("Test smazání uživatele pomocí neexistujícího ID.")
    public void deleteUserByNonExistingID() {
        when(userService.deleteUserByID(anyLong())).thenReturn(0);

        // Test
        ResponseEntity<String> test = userController.deleteUserById(1L);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
        assertEquals("User with ID 1 was not found.", test.getBody());

        verify(userService, times(1)).deleteUserByID(anyLong());
    }
}
