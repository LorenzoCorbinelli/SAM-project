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
	
	public Student getStudentInfoById(Long id) {
		return studentDAO.findById(id);
	}
	
	public void registerStudent(Student student) {
		studentDAO.save(student);
	}
	
	public Student registerStudent(String name, String surname) {
		Student student = new Student(name, surname, name.toLowerCase() +"." + surname.toLowerCase() +"@example.com");
		studentDAO.save(student);
		return student;
	}
	
	public void deleteStudent(Student student) {
		studentDAO.delete(student);
	}
	
}
