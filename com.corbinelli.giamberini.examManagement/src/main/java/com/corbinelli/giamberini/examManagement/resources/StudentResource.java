package com.corbinelli.giamberini.examManagement.resources;

import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.services.StudentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
	
	@Inject
	private StudentService studentService;
	
	@GET
	@Path("/{id}")
	public Student getStudent(@PathParam("id") Long id) {
		return studentService.getStudentInfoById(id);
	}

}
