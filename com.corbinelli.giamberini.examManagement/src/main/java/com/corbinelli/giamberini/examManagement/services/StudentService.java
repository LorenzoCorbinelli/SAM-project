package com.corbinelli.giamberini.examManagement.services;

import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.StudentDAO;
import com.corbinelli.giamberini.examManagement.model.Student;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@RequestScoped
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
		List<Student> resultList = studentDAO.findByNameAndSurname(name, surname);
		String emailNumber = "";
		if(!resultList.isEmpty()) {
			emailNumber = resultList.size() + "";
		}
		
		Student student = new Student(name, 
				surname, 
				name.toLowerCase() +"." + surname.toLowerCase() + emailNumber + "@example.com");
		
		studentDAO.save(student);
		return student;
	}
	
	public Student deleteStudent(Long id) {
		Student studentToDelete = studentDAO.findById(id);
		if(studentToDelete == null) {
			throw new EntityNotFoundException("Student not found with id: " + id);
		}
		studentDAO.delete(studentToDelete);
		return studentToDelete;
	}
	
}
