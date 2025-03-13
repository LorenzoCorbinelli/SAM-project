package com.corbinelli.giamberini.examManagement.resources;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.corbinelli.giamberini.examManagement.model.Student;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class StudentResourceTest extends JerseyTest {
	
	private static Connection connection;
	
	@Override
	protected Application configure() {
		return new ResourceConfig(StudentResource.class);
	}
	
	@BeforeClass
	public static void setup() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/examManagementDB", 
					"user",
					"user");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testNewStudent() {
		Student student = new Student("Mario", "Rossi", "mario.rossi@example.com");
		Response post = target("/students")
				.request()
				.post(Entity.entity(student, MediaType.APPLICATION_JSON));
		
		assertEquals(201, post.getStatus());
		
		try {
			Statement statement = connection.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");
			statement.executeUpdate("TRUNCATE TABLE students");
			statement.execute("SET FOREIGN_KEY_CHECKS = 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetStudent() {
		insertStudent(new Student("Mario", "Rossi", "mario.rossi@example.com"));
		Student student2 = new Student("Luigi", "Verdi", "luigi.verdi@example.com");
		insertStudent(student2);
		
		Response response = target("/students/2").request().get();
		assertEquals(200, response.getStatus());
		Entity<Student> entity = Entity.entity(student2, MediaType.APPLICATION_JSON);
		assertEquals(entity, response.getEntity());
	}
	
	private void insertStudent(Student student) {
		String query = "INSERT INTO students (name, surname, email) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, student.getName());
			statement.setString(2, student.getSurname());
			statement.setString(3, student.getEmail());
			int result = statement.executeUpdate();
			
			if (result > 0) {
				System.out.println("Student inserted successfully.");
			} else {
				System.out.println("Failed to insert student.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
