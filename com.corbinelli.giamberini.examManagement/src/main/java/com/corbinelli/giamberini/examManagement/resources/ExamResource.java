package com.corbinelli.giamberini.examManagement.resources;


import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.services.ExamService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
		Exam removedExam = examService.removeExam(id);
		if(removedExam == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(removedExam)
				.build();
	}

}
