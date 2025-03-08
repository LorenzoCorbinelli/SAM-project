package com.corbinelli.giamberini.examManagement.resources;


import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.services.ExamService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/exams")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamResource {
	
	@Inject
	private ExamService examService;
	
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

}
