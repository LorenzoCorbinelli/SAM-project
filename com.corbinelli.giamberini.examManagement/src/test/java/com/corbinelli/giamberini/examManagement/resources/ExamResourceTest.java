package com.corbinelli.giamberini.examManagement.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.corbinelli.giamberini.examManagement.RestServer;
import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ExamResourceTest {

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
	public void testExamResource() {
		// GET all exams: NO CONTENT
		Response response = target.path("/exams/all").request().get();
		assertEquals(204, response.getStatus());
		
		// POST: BAD REQUEST COURSE NOT FOUND
		Course course = new Course("AST", "Automated Software Testing", null);
		Exam exam = new Exam(course, LocalDate.of(2025, 5, 20));
		response = target.path("/exams").request().post(Entity.entity(exam, MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());

		// POST
		Teacher teacher = new Teacher("Neri", "Gigli", "neri.gigli@example.com");
		Response postTeacher = target.path("/teachers").request()
				.post(Entity.entity(teacher, MediaType.APPLICATION_JSON));
		teacher.setId(postTeacher.readEntity(Teacher.class).getId());

		// POST: Create a new course
		course = new Course("AST", "Automated Software Testing", teacher);
		response = target.path("/courses").request().post(Entity.entity(course, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		course.setId(response.readEntity(Course.class).getId());

		exam = new Exam(course, LocalDate.of(2025, 5, 20));
		response = target.path("/exams").request().post(Entity.entity(exam, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		exam.setId(response.readEntity(Exam.class).getId());

		// GET exam by ID
		response = target.path("/exams/id/" + exam.getId()).request().get();
		assertEquals(200, response.getStatus());
		Exam retrievedExam = response.readEntity(Exam.class);
		assertEquals(exam.getDate(), retrievedExam.getDate());
		assertEquals(exam.getCourse().getId(), retrievedExam.getCourse().getId());

		// GET by ID: NOT FOUND
		response = target.path("/exams/id/2").request().get();
		assertEquals(404, response.getStatus());

		// GET exams for a course
		Exam exam2 = new Exam(course, LocalDate.of(2025, 6, 15));
		response = target.path("/exams").request().post(Entity.entity(exam2, MediaType.APPLICATION_JSON));
		exam2.setId(response.readEntity(Exam.class).getId());

		response = target.path("/exams/course/" + course.getId()).request().get();
		assertEquals(200, response.getStatus());
		List<LocalDate> examDates = response.readEntity(new GenericType<List<LocalDate>>() {});
		assertThat(examDates).containsExactlyInAnyOrder(exam.getDate(), exam2.getDate());

		// GET exams for a course: NO CONTENT
		response = target.path("/exams/course/999").request().get();
		assertEquals(204, response.getStatus());
		
		// GET all exams
		response = target.path("/exams/all").request().get();
		List<Exam> allExams = response.readEntity(new GenericType<List<Exam>>() {});
		assertEquals(200, response.getStatus());
		assertThat(allExams).containsExactlyInAnyOrder(exam, exam2);

		// GET exams on a specific date
		response = target.path("/exams/date/2025-05-20").request().get();
		assertEquals(200, response.getStatus());
		List<Course> coursesOnDate = response.readEntity(new GenericType<List<Course>>() {
		});
		assertThat(coursesOnDate).containsExactly(course);

		// GET exams on a specific date: NO CONTENT
		response = target.path("/exams/date/2025-06-10").request().get();
		assertEquals(204, response.getStatus());
		
		// GET exams in a period
		response = target.path("/exams/period").queryParam("start", "2025-05-01").queryParam("end", "2025-06-30")
				.request().get();
		assertEquals(200, response.getStatus());
		List<Exam> examsInPeriod = response.readEntity(new GenericType<List<Exam>>() {});
		assertThat(examsInPeriod).containsExactlyInAnyOrder(exam, exam2);

		// GET exams in a period: NO CONTENT
		response = target.path("/exams/period").queryParam("start", "2024-01-01").queryParam("end", "2024-12-31")
				.request().get();
		assertEquals(204, response.getStatus());

		// GET exams in a period: BAD REQUEST
		response = target.path("/exams/period").queryParam("start", "2025-07-01").queryParam("end", "2025-06-01")
				.request().get();
		assertEquals(400, response.getStatus());
		
		// DELETE the exam
		response = target.path("/exams/" + exam.getId()).request().delete();
		assertEquals(200, response.getStatus());

		// DELETE: NOT FOUND
		response = target.path("/exams/" + exam.getId()).request().delete();
		assertEquals(404, response.getStatus());
	}

}
