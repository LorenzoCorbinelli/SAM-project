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
import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class EnrollmentResourceTest {

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
	public void testEnrollmentResource() {
		// POST new enrollment
	    Student student1 = new Student("Luigi", "Verdi", "luigi.verdi@example.com");
	    Response response = target.path("/students")
	    		.request()
	            .post(Entity.entity(student1, MediaType.APPLICATION_JSON));
	    student1.setId(response.readEntity(Student.class).getId());
	    
	    Student student2 = new Student("Mario", "Rossi", "mario.rossi@example.com");
	    response = target.path("/students")
	    		.request()
	            .post(Entity.entity(student2, MediaType.APPLICATION_JSON));
	    student2.setId(response.readEntity(Student.class).getId());
	    
	    Teacher teacher = new Teacher("Neri", "Gigli", "neri.gigli@example.com");
	    response = target.path("/teachers")
	        .request()
	        .post(Entity.entity(teacher, MediaType.APPLICATION_JSON));
	    teacher.setId(response.readEntity(Teacher.class).getId());
	    
	    Course course1 = new Course("AST", "Automated Software Testing", teacher);
	    response = target.path("/courses")
	        .request()
	        .post(Entity.entity(course1, MediaType.APPLICATION_JSON));
	    course1.setId(response.readEntity(Course.class).getId());
	    
	    Course course2 = new Course("RRTC", "Resiliency Real-Time and Certification", teacher);
	    response = target.path("/courses")
	        .request()
	        .post(Entity.entity(course2, MediaType.APPLICATION_JSON));
	    course2.setId(response.readEntity(Course.class).getId());
	    
	    Exam exam1 = new Exam(course1, LocalDate.of(2025, 5, 20));
	    response = target.path("/exams")
	        .request()
	        .post(Entity.entity(exam1, MediaType.APPLICATION_JSON));;
	    exam1.setId(response.readEntity(Exam.class).getId());
	    
	    Exam exam2 = new Exam(course2, LocalDate.of(2025, 5, 20));
	    response = target.path("/exams")
	        .request()
	        .post(Entity.entity(exam2, MediaType.APPLICATION_JSON));;
	    exam2.setId(response.readEntity(Exam.class).getId());
	    
	    Exam exam3 = new Exam(course2, LocalDate.of(2025, 6, 15));
	    response = target.path("/exams")
	        .request()
	        .post(Entity.entity(exam3, MediaType.APPLICATION_JSON));;
	        exam3.setId(response.readEntity(Exam.class).getId());
	    
	    Enrollment enrollment1 = new Enrollment(student1, exam1);
	    response = target.path("/enrollments")
	    		.request()
	            .post(Entity.entity(enrollment1, MediaType.APPLICATION_JSON));
	    assertEquals(201, response.getStatus());
	    
	    Long enrollmentId = response.readEntity(Enrollment.class).getId();
	    enrollment1.setId(enrollmentId);
	    
	    // GET enrollment by ID
	    response = target.path("/enrollments/id/" + enrollmentId).request().get();
	    Enrollment retrievedEnrollment = response.readEntity(Enrollment.class);
	    assertEquals(200, response.getStatus());
	    assertEquals(enrollment1.getStudent(), retrievedEnrollment.getStudent());
	    assertEquals(enrollment1.getExam(), retrievedEnrollment.getExam());
	    
	    // GET enrollment by ID - NOT FOUND
	    response = target.path("/enrollments/id/999").request().get();
	    assertEquals(404, response.getStatus());
	    
	    // GET enrollment by student ID
	    Enrollment enrollment2 = new Enrollment(student1, exam3);
	    response = target.path("/enrollments")
	    		.request()
	            .post(Entity.entity(enrollment2, MediaType.APPLICATION_JSON));
	    enrollment2.setId(response.readEntity(Enrollment.class).getId());
	    
	    response = target.path("/enrollments/student/" + student1.getId()).request().get();
	    List<Exam> exams = response.readEntity(new GenericType<List<Exam>>() {});
	    assertEquals(200, response.getStatus());
	    assertThat(exams).containsExactly(enrollment1.getExam(), enrollment2.getExam());

	    // GET enrollment by student ID: NOT FOUND
	    response = target.path("/enrollments/student/999").request().get();
	    assertEquals(404,response.getStatus());
	    
	    // GET enrollment by exam ID
	    Enrollment enrollment3 = new Enrollment(student2, exam1);
	    response = target.path("/enrollments")
	    		.request()
	            .post(Entity.entity(enrollment3, MediaType.APPLICATION_JSON));
	    enrollment3.setId(response.readEntity(Enrollment.class).getId());
	    
	    response = target.path("/enrollments/exam/" + exam1.getId()).request().get();
	    List<Student> students = response.readEntity(new GenericType<List<Student>>() {});
	    assertEquals(200, response.getStatus());
	    assertThat(students).containsExactly(enrollment1.getStudent(),enrollment3.getStudent());

	    // GET enrollment by exam ID: NOT FOUND
	    response = target.path("/enrollments/exam/111").request().get();
	    students = response.readEntity(new GenericType<List<Student>>() {});
	    System.out.println(students);
	    assertEquals(404,response.getStatus());
	    
	    // GET exam trials
	    response = target.path("/enrollments/trials").queryParam("student", student1.getId())
	            .queryParam("course", course1.getId()).request().get();
	    List<Exam> trialsForStudent1Course1 = response.readEntity(new GenericType<List<Exam>>() {});
	    assertThat(trialsForStudent1Course1).containsExactly(exam1);
	    
	    // GET exam trials: NO CONTENT
	    response = target.path("/enrollments/trials").queryParam("student", "999")
	            .queryParam("course", "000").request().get();
	    assertEquals(204,response.getStatus());
	    
	    // DELETE enrollment
	    response = target.path("/enrollments/" + enrollmentId).request().delete();
	    assertEquals(200, response.getStatus());

	    // DELETE enrollment: NOT FOUND
	    response = target.path("/enrollments/" + enrollmentId).request().delete();
	    assertEquals(404, response.getStatus());
	}


}
