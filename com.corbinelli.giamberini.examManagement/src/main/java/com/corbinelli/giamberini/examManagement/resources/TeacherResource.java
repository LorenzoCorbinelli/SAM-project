package com.corbinelli.giamberini.examManagement.resources;


import com.corbinelli.giamberini.examManagement.model.Teacher;
import com.corbinelli.giamberini.examManagement.services.TeacherService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/teachers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherResource {

	@Inject
	private TeacherService teacherService;

	@GET
	@Path("/{id}")
	public Response getTeacher(@PathParam("id") Long id) {
		Teacher teacher = teacherService.getTeacherInfoById(id);
		if (teacher == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(teacher)
				.build();
	}
	
	@POST
	public Response newTeacher(Teacher teacher) {
		teacherService.registerTeacher(teacher);
		return Response.status(Response.Status.CREATED)
				.entity(teacher)
				.build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeTeacher(@PathParam("id") Long id) {
		try {
			Teacher teacher = teacherService.deleteTeacher(id);
			return Response.status(Response.Status.OK)
					.entity(teacher)
					.build();
		} catch(EntityNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity(e.getMessage())
					.build();
		}
	}
}
