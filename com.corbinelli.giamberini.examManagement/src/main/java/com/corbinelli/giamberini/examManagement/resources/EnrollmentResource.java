package com.corbinelli.giamberini.examManagement.resources;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.services.EnrollmentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/enrollments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnrollmentResource {

	@Inject
	private EnrollmentService enrollmentService;
	
	@GET
	@Path("/id/{id}")
	public Response getEnrollment(@PathParam("id") Long id) {
		Enrollment enrollment = enrollmentService.getEnrollmentInfoById(id);
		if (enrollment == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(enrollment)
				.build();
	}
	
	@GET
	@Path("/student/{id}")
	public Response getEnrollmentOfStudent(@PathParam("id") Long studentID) {
		List<Exam> enrollmentsByStudent = enrollmentService.getExamsByStudent(studentID);
		if(enrollmentsByStudent.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(enrollmentsByStudent)
				.build();
	}
	
	@GET
	@Path("/exam/{id}")
	public Response getEnrollmentOfExam(@PathParam("id") Long examID) {
		List<Student> studentsEnrolledForAnExam = enrollmentService.getStudentsEnrolledForAnExam(examID);
		if (studentsEnrolledForAnExam.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(studentsEnrolledForAnExam)
				.build();
	}

	@GET
	@Path("/trials")
	public Response getExamsInAPeriod(@QueryParam("student") Long studentID, @QueryParam("course") Long courseID) {
		List<Exam> trialsForAnExamByStudentAndCourse = enrollmentService.getTrialsForAnExamByStudentAndCourse(studentID,courseID);
		if(trialsForAnExamByStudentAndCourse.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(trialsForAnExamByStudentAndCourse)
				.build();
	}
	
	@POST
	public Response newEnrollment(Enrollment enrollment) {
		try {
			enrollmentService.enroll(enrollment);
			return Response.status(Response.Status.CREATED)
					.entity(enrollment)
					.build();
		} catch(IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(enrollment)
					.build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeEnrollment(@PathParam("id") Long id) {
		Enrollment disenroll = enrollmentService.disenroll(id);
		if (disenroll == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(disenroll)
				.build();
	}

}
