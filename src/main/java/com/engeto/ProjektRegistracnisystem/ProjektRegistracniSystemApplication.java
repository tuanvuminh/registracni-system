package com.engeto.ProjektRegistracnisystem;

import com.engeto.ProjektRegistracnisystem.exceptions.UserException;
import com.engeto.ProjektRegistracnisystem.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class ProjektRegistracniSystemApplication {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, UserException {
		SpringApplication.run(ProjektRegistracniSystemApplication.class, args);
	}
}

