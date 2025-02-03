package com.corbinelli.giamberini.examManagement.services;

import com.corbinelli.giamberini.examManagement.daos.StudentDAO;
import com.corbinelli.giamberini.examManagement.model.Student;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudentService {

	@Inject
	private StudentDAO studentDAO;
	
	public StudentService() {}
	
	public void registerStudent(Student student) {
		studentDAO.save(student);
	}

}
