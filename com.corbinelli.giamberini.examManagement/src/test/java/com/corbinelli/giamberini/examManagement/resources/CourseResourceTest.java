package com.corbinelli.giamberini.examManagement.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.corbinelli.giamberini.examManagement.RestServer;
import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class CourseResourceTest {

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
	public void testCourseResourceTest() {
		//GET all --> empty list
		Response response = target.path("/courses/all").request().get();
		assertEquals(204,response.getStatus());
		
		// POST: BAD REQUEST TEACHER NOT FOUND
		Course course = new Course("AST","Automated Software Testing", new Teacher("", "", ""));
		response = target.path("/courses")
				.request()
				.post(Entity.entity(course, MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
		
		//POST
		Teacher teacher = new Teacher("Neri","Gigli","neri.gigli@example.com");
		Response postTeacher = target.path("/teachers")
			.request()
			.post(Entity.entity(teacher, MediaType.APPLICATION_JSON));
		teacher.setId(postTeacher.readEntity(Teacher.class).getId());
		
		course = new Course("AST","Automated Software Testing",teacher);
		response = target.path("/courses")
				.request()
				.post(Entity.entity(course, MediaType.APPLICATION_JSON));
		
		assertEquals(201, response.getStatus());
		
		//GET by ID
		Long courseId = response.readEntity(Course.class).getId();
		course.setId(courseId); //set the Id of the course that was saved
		
		response = target.path("/courses/id/"+courseId).request().get();
		Course retrivedEntity = response.readEntity(Course.class);
	
		assertEquals(200, response.getStatus());
		assertEquals(course.getName(), retrivedEntity.getName());
		assertEquals(course.getDescription(), retrivedEntity.getDescription());
		assertEquals(course.getTeacher(), retrivedEntity.getTeacher());
		
		//GET by ID: NOT FOUND
		response = target.path("/courses/id/2").request().get();
		assertEquals(404, response.getStatus());
		
		//GET by name
		String courseName = course.getName();
		
		response = target.path("/courses/name/"+courseName).request().get();
		List<Course> listRetrivedCourse = response.readEntity(new GenericType<List<Course>>() {});
		assertEquals(200, response.getStatus());
		assertThat(listRetrivedCourse).containsExactly(course);
		
		//GET by name: NOT FOUND
		response = target.path("/courses/name/a").request().get();
		assertEquals(404, response.getStatus());
		
		//GET all
		Course course2 = new Course("RRTC","Resiliency Real-Time and Certification",teacher);
		response = target.path("/courses")
			.request()
			.post(Entity.entity(course2, MediaType.APPLICATION_JSON));
		
		course2.setId(response.readEntity(Course.class).getId()); //set the Id of the course that was saved
		
		response = target.path("/courses/all").request().get();
		listRetrivedCourse = response.readEntity(new GenericType<List<Course>>() {});
		assertThat(listRetrivedCourse).containsExactlyInAnyOrder(course2, course);
		
		// GET by teacher: NO CONTENT
		response = target.path("/courses/teacher/99").request().get();
		assertEquals(204, response.getStatus());
		
		// GET by teacher
		response = target.path("/courses/teacher/" + teacher.getId()).request().get();
		listRetrivedCourse = response.readEntity(new GenericType<List<Course>>() {});
		assertThat(listRetrivedCourse).containsExactlyInAnyOrder(course, course2);
				
		//DELETE
		response = target.path("/courses/"+courseId).request().delete();
		
		assertEquals(200, response.getStatus());
		
		//DELETE: NOT FOUND
		response = target.path("/courses/"+courseId).request().delete();
		
		assertEquals(404, response.getStatus());
		
	}

}
