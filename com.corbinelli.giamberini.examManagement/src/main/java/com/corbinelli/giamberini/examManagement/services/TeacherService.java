package com.corbinelli.giamberini.examManagement.services;

import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.TeacherDAO;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@RequestScoped
public class TeacherService {

	@Inject
	private TeacherDAO teacherDAO;
	
	public TeacherService() {}

	public void registerTeacher(Teacher teacher) {
		teacherDAO.save(teacher);
	}
	
	public Teacher registerTeacher(String name, String surname) {
		List<Teacher> resultList = teacherDAO.findByNameAndSurname(name, surname);
		String emailNumber = "";
		if(!resultList.isEmpty()) {
			emailNumber = resultList.size() + "";
		}
		
		Teacher teacher = new Teacher(name,
				surname, 
				name.toLowerCase() + "." + surname.toLowerCase() + emailNumber + "@example.com");
		
		teacherDAO.save(teacher);
		return teacher;
	}
	
	public Teacher deleteTeacher(Long id) {
		Teacher teacherToRemove = teacherDAO.findById(id);
		if(teacherToRemove == null) {
			throw new EntityNotFoundException("Teacher not found with id: " + id);
		}
		teacherDAO.delete(teacherToRemove);
		return teacherToRemove;
	}
	
	public Teacher getTeacherInfoById(Long id) {
		return teacherDAO.findById(id);
	}
}
