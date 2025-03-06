package com.corbinelli.giamberini.examManagement.resources;

import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.services.StudentService;

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

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
	
	@Inject
	private StudentService studentService;
	
	@GET
	@Path("/{id}")
	public Response getStudent(@PathParam("id") Long id) {
		Student student = studentService.getStudentInfoById(id);
		if(student == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(student)
				.build();
	}
	
	@POST
	public Response newStudent(Student student) {
		studentService.registerStudent(student);
		return Response.status(Response.Status.CREATED)
				.entity(student)
				.build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeStudent(@PathParam("id") Long id) {
		studentService.deleteStudent(id);
		return Response.status(Response.Status.OK)
				.build();
	}
	
}
