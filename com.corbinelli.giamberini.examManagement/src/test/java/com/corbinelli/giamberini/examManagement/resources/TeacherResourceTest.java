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
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class TeacherResourceTest {
	
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
					TimeUnit.SECONDS.sleep(2);
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
	public void testTeacherResource() {
		// POST
		Teacher teacher = new Teacher("Mario", "Rossi", "mario.rossi@example.com");
		Response response = target.path("/teachers")
			.request()
			.post(Entity.entity(teacher, MediaType.APPLICATION_JSON));
		
		assertEquals(201, response.getStatus());
		
		Teacher entity = response.readEntity(Teacher.class);
		Long teacherId = entity.getId();
		// GET
		response = target.path("/teachers/" + teacherId).request().get();
		assertEquals(200, response.getStatus());
		assertEquals(teacher.getName(), entity.getName());
		assertEquals(teacher.getSurname(), entity.getSurname());
		assertEquals(teacher.getEmail(), entity.getEmail());
		
		// DELETE
		response = target.path("/teachers/" + teacherId).request().delete();
		assertEquals(200, response.getStatus());
		
		// DELETE: NOT FOUND
		response = target.path("/teachers/" + teacherId).request().delete();
		assertEquals(404, response.getStatus());
		
		// GET: NOT FOUND
		response = target.path("/teachers/" + teacherId).request().get();
		assertEquals(404, response.getStatus());
	}

}
