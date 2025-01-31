package com.corbinelli.giamberini.examManagement;


import java.time.LocalDate;

import com.corbinelli.giamberini.examManagement.daos.CourseDAO;
import com.corbinelli.giamberini.examManagement.daos.ExamDAO;
import com.corbinelli.giamberini.examManagement.daos.TeacherDAO;
import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		// Create an EntityManagerFactory for the persistence unit
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
		EntityManager em = emf.createEntityManager();
		
		Teacher teacher1 = new Teacher("Mario", "Rossi", "mario.rossi@example.com");
		Teacher teacher2 = new Teacher("Luigi", "Verdi", "luigi.verdi@example.com");
		
		Course course1 = new Course("PT", "Penetration Testing", teacher1);
		Course course2 = new Course("SAM", "SAM", teacher2);
		Course course3 = new Course("AST", "AST", teacher2);
		
		TeacherDAO teacherDAO = new TeacherDAO(em);
		teacherDAO.save(teacher1);
		teacherDAO.save(teacher2);
		
		CourseDAO courseDAO = new CourseDAO(em);
		courseDAO.save(course1);
		courseDAO.save(course2);
		courseDAO.save(course3);
		
		Exam exam1 = new Exam(course1, LocalDate.parse("2025-01-10"));
		Exam exam2 = new Exam(course1, LocalDate.parse("2025-01-31"));
		Exam exam3 = new Exam(course2, LocalDate.parse("2025-01-31"));
		Exam exam4 = new Exam(course3, LocalDate.parse("2025-02-05"));
		
		ExamDAO examDAO = new ExamDAO(em);
		examDAO.save(exam1);
		examDAO.save(exam2);
		examDAO.save(exam3);
		examDAO.save(exam4);
		
		System.out.println(examDAO.findAll());
		System.out.println(examDAO.findExamCoursesByDate(LocalDate.parse("2025-01-31")));
		System.out.println(examDAO.findExamDatesByCourse(course1));
		System.out.println(examDAO.findExamsInAPeriod(LocalDate.parse("2025-01-15"), LocalDate.parse("2025-02-15")));

		// Close the EntityManager
		em.close();
		emf.close();
	}

}
