package com.corbinelli.giamberini.examManagement.services;

import com.corbinelli.giamberini.examManagement.daos.TeacherDAO;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TeacherService {

	@Inject
	private TeacherDAO teacherDAO;
	
	public TeacherService() {}

	public void registerTeacher(Teacher teacher) {
		teacherDAO.save(teacher);
	}
	
	public void deleteTeacher(Teacher teacher) {
		teacherDAO.delete(teacher);
	}
	
	public Teacher getTeacherInfoById(Long id) {
		return teacherDAO.findById(id);
	}
}
