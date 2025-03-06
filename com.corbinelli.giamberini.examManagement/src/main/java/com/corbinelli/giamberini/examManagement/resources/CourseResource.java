package com.corbinelli.giamberini.examManagement.resources;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.services.CourseService;

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

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

	@Inject
	private CourseService courseService;

	@GET
	@Path("/id/{id}")
	public Response getCourse(@PathParam("id") Long id) {
		Course course = courseService.getCourseInfoById(id);
		if (course == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(course)
				.build();
	}
	
	@GET
	@Path("/name/{name}")
	public Response getCourse(@PathParam("name") String name) {
		List<Course> coursesByName = courseService.getCoursesByName(name);
		if (coursesByName.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(coursesByName)
				.build();
	}
	
	@POST
	public Response newCourse(Course course) {
		try {
			courseService.insertCourse(course);
			return Response.status(Response.Status.CREATED)
					.entity(course)
					.build();
		} catch(IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(course)
					.build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeCourse(@PathParam("id") Long id) {
		Course course = courseService.removeCourse(id);
		if (course == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.build();
		}
		return Response.status(Response.Status.OK)
				.entity(course)
				.build();
	}
}
