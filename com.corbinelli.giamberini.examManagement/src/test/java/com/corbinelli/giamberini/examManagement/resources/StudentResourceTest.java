package com.corbinelli.giamberini.examManagement.resources;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.corbinelli.giamberini.examManagement.RestServer;
import com.corbinelli.giamberini.examManagement.model.Student;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class StudentResourceTest {
	
	private static Client client;
	private static WebTarget target;
	private static final String PATH_TO_DOCKER_COMPOSE = "src/test/resources/docker-compose.yml";
	
	@BeforeClass
	public static void initialize() {
		ProcessBuilder pbUp = new ProcessBuilder("docker-compose", "-f", PATH_TO_DOCKER_COMPOSE, "up", "-d");
		pbUp.inheritIO();
		try {
			pbUp.start().waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		waitForDatabase();
		
		RestServer.main(null);
		ClientConfig clientConfig = new ClientConfig();
		client = JerseyClientBuilder.createClient(clientConfig);
		target = client.target("http://localhost:8080/");
	}
	
	private static void waitForDatabase() {
		int retries = 10; // Maximum retries before failing
		while (retries > 0) {
			try (Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/examManagementDB", 
					"user", 
					"user")) {
				return;
			} catch (SQLException e) {
				retries--;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					throw new RuntimeException("Database wait interrupted", ie);
				}
			}
		}
		throw new IllegalStateException("Could not connect to the database after multiple attempts.");
	}
	
	@AfterClass
	public static void teardown() {
		RestServer.shutDown();
		ProcessBuilder pbDown = new ProcessBuilder("docker-compose", "-f", PATH_TO_DOCKER_COMPOSE, "down");
		pbDown.inheritIO();
		try {
			pbDown.start().waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStudentResource() {
		// POST
		Student student = new Student("Mario", "Rossi", "mario.rossi@example.com");
		Response response = target.path("/students")
				.request()
				.post(Entity.entity(student, MediaType.APPLICATION_JSON));
		
		assertEquals(201, response.getStatus());
		
		// GET
		response = target.path("/students/1").request().get();
		
		assertEquals(200, response.getStatus());
		Student entity = response.readEntity(Student.class);
		assertEquals(student.getName(), entity.getName());
		assertEquals(student.getSurname(), entity.getSurname());
		assertEquals(student.getEmail(), entity.getEmail());
		
		// DELETE
		response = target.path("/students/1").request().delete();
		
		assertEquals(200, response.getStatus());
		response = target.path("/students/1").request().delete();
		assertEquals(404, response.getStatus());
		
		response = target.path("/students/1").request().get();
		assertEquals(404, response.getStatus());
	}
	
}
