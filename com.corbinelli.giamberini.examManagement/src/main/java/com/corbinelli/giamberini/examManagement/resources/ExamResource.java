package com.corbinelli.giamberini.examManagement.resources;


import java.time.LocalDate;
import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.services.ExamService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
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

@Path("/exams")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamResource {
	
	@Inject
	private ExamService examService;
	
	@GET
	@Path("/id/{id}")
	public Response getExam(@PathParam("id") Long id) {
		Exam exam = examService.getExamInfoById(id);
		if(exam == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(exam)
				.build();
	}
	
	@GET
	@Path("/all")
	public Response getAllExams() {
		List<Exam> allExams = examService.getAllExams();
		if(allExams.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(allExams)
				.build();
	}
	
	@GET
	@Path("/course/{id}")
	public Response getExamsDatesOfCourse(@PathParam("id") Long courseID) {
		List<LocalDate> examDatesOfCourse = examService.getExamDatesOfCourse(courseID);
		if(examDatesOfCourse.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(examDatesOfCourse)
				.build();
	}
	
	@GET
	@Path("/date/{date}")
	public Response getExamCoursesInDate(@PathParam("date") String date) {
		List<Course> examCoursesInDate = examService.getExamCoursesInDate(date);
		if(examCoursesInDate.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(examCoursesInDate)
				.build();
	}
	
	@GET
	@Path("/period")
	public Response getExamsInAPeriod(@QueryParam("start") String startDate, @QueryParam("end") String endDate) {
		try {
			List<Exam> examsInAPeriod = examService.getExamsInAPeriod(startDate, endDate);

			if (examsInAPeriod.isEmpty()) {
				return Response.status(Response.Status.NO_CONTENT).build();
			}

			return Response.status(Response.Status.OK).entity(examsInAPeriod).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid dates order").build();
		}
	}
	
	@POST
	public Response newExam(Exam exam) {
		try {
			examService.insertExam(exam);
			return Response.status(Response.Status.CREATED)
					.entity(exam)
					.build();
		} catch(IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(exam)
					.build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeExam(@PathParam("id") Long id) {
		try {
			Exam removedExam = examService.removeExam(id);
			return Response.status(Response.Status.OK)
					.entity(removedExam)
					.build();
		} catch(EntityNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity(e.getMessage())
					.build();
		}
	}

}
