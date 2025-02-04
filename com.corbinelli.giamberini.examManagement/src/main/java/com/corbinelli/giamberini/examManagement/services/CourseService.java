package com.corbinelli.giamberini.examManagement.services;

import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.CourseDAO;
import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CourseService {
	
	@Inject
	private CourseDAO courseDAO;

	public CourseService() {}
	
	public void insertCourse(Course course) {
		courseDAO.save(course);
	}
	
	public void removeCourse(Course course) {
		courseDAO.delete(course);
	}
	
	public Course getCourseInfoById(Long id) {
		return courseDAO.findById(id);
	}
	
	public List<Course> getAllCourses() {
		return courseDAO.findAll();
	}
	
	public List<Course> getCoursesOfTeacher(Teacher teacher) {
		return courseDAO.findCoursesByTeacher(teacher);
	}

}
