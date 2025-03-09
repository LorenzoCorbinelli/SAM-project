package com.corbinelli.giamberini.examManagement.resources;

import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.services.EnrollmentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/enrollments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnrollmentResource {

	@Inject
	private EnrollmentService enrollmentService;
	
	//get by id
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
	
	//get by student
	@GET
	@Path("/student/{id}")
	public Response getEnrollmentOfStudent(@PathParam("id") Long id) {
		Enrollment enrollment = enrollmentService.getEnrollmentInfoById(id);
		if (enrollment == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(enrollment)
				.build();
	}
	//get by exam
	//get trials given exam and course
	
	//post enroll
	@POST
	public Response newEnrollment(Enrollment enrollment) {
		System.out.println(enrollment);
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
	
	//delete disenroll
	

}
