package com.corbinelli.giamberini.examManagement.services;

import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.CourseDAO;
import com.corbinelli.giamberini.examManagement.daos.TeacherDAO;
import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CourseService {
	
	@Inject
	private CourseDAO courseDAO;
	
	@Inject
	private TeacherDAO teacherDAO;

	public CourseService() {}
	
	public void insertCourse(Course course) {
		Long teacherID = course.getTeacher().getId();
		Teacher existingTeacher = teacherDAO.findById(teacherID);
		if(existingTeacher == null) {
			throw new IllegalArgumentException("Teacher not found with ID: " + teacherID);
		}
		course.setTeacher(existingTeacher);
		courseDAO.save(course);
	}
	
	public Course insertCourse(String name, String description, Teacher teacher) {
		Course course = new Course(name, description, teacher);
		courseDAO.save(course);
		return course;
	}
	
	public Course removeCourse(Long id) {
		return courseDAO.delete(id);
	}
	
	public Course getCourseInfoById(Long id) {
		return courseDAO.findById(id);
	}
	
	public List<Course> getAllCourses() {
		return courseDAO.findAll();
	}
	
	public List<Course> getCoursesOfTeacher(Long teacherID) {
		return courseDAO.findCoursesByTeacher(teacherID);
	}

	public List<Course> getCoursesByName(String name) {
		return courseDAO.findCoursesByName(name);
	}
	
	public List<Course> getCoursesByPartialDescription(String partialDescription) {
		return courseDAO.findCoursesByPartialDescription(partialDescription);
	}
}
