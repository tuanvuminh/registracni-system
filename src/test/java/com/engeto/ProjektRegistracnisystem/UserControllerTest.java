package com.engeto.ProjektRegistracnisystem;
import com.engeto.ProjektRegistracnisystem.controllers.UserController;
import com.engeto.ProjektRegistracnisystem.exceptions.UserException;
import com.engeto.ProjektRegistracnisystem.model.User;
import com.engeto.ProjektRegistracnisystem.service.UserService;
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
    public void printHelloWorld() {
        System.out.println("Hello world!");
    }

    @Test
    public void addUserWithValidPersonID() throws UserException {
        User user = new User("Jake", "Sully", "jXa4g3H7oPq2");
        when(userService.createUser(any())).thenReturn(1);

        ResponseEntity<String> response = userController.addNewUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User was added successfully.", response.getBody());

        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testGetUserByIdExistingUser() throws UserException {
        // Mockování chování userService
        User user = new User("Jake", "Sully", "jXa4g3H7oPq2");
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(user);

        // Test
        ResponseEntity<Object> response = userController.getUserById(1L, true);

        // Ověření
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
    }

    @Test
    public void testGetUserByIdNonExistingUser() {
        // Mockování chování userService
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(null);

        // Test
        ResponseEntity<Object> response = userController.getUserById(1L, true);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 1 was not found.", response.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
    }

    @Test
    public void testGetUsersListNotEmpty() throws UserException {
        // Mockování chování userService
        List<Object> userList = new ArrayList<>();
        userList.add(new User("Jake", "Sully", "jXa4g3H7oPq2"));
        when(userService.getUsersList(anyBoolean())).thenReturn(userList);

        // Test
        ResponseEntity<List<Object>> response = userController.getUsersList(true);

        // Ověření
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());

        verify(userService, times(1)).getUsersList(anyBoolean());
    }

    @Test
    public void testUpdateExistingUser() throws UserException {
        // Mockování chování userService
        User existingUser = new User(/* nastavte potřebné atributy */);
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(existingUser);

        // Test
        User updatedUserData = new User("UpdatedName", "UpdatedSurname", "UpdatedPersonID");
        ResponseEntity<String> response = userController.updateUser(1L, updatedUserData);

        // Ověření
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User was updated successfully.", response.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
        verify(userService, times(1)).updateUser(any());
    }

    @Test
    public void testUpdateNonExistingUser() throws UserException {
        // Mockování chování userService
        when(userService.getUserDetails(anyLong(), anyBoolean())).thenReturn(null);

        // Test
        User updatedUserData = new User("UpdatedName", "UpdatedSurname", "UpdatedPersonID");
        ResponseEntity<String> response = userController.updateUser(1L, updatedUserData);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 1 was not found.", response.getBody());

        verify(userService, times(1)).getUserDetails(anyLong(), anyBoolean());
        verify(userService, never()).updateUser(any());
    }

    @Test
    public void testDeleteExistingUser() {
        // Mockování chování userService
        when(userService.deleteUserByID(anyLong())).thenReturn(1);

        // Test
        ResponseEntity<String> response = userController.deleteUserById(1L);

        // Ověření
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User was deleted successfully.", response.getBody());

        verify(userService, times(1)).deleteUserByID(anyLong());
    }

    @Test
    public void testDeleteNonExistingUser() {
        // Mockování chování userService
        when(userService.deleteUserByID(anyLong())).thenReturn(0);

        // Test
        ResponseEntity<String> response = userController.deleteUserById(1L);

        // Ověření
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 1 was not found.", response.getBody());

        verify(userService, times(1)).deleteUserByID(anyLong());
    }
}
