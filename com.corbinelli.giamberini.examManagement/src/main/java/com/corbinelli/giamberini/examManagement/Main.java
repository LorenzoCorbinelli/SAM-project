package com.corbinelli.giamberini.examManagement;

import java.time.LocalDate;
import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {

	public static void main(String[] args) {
		// Create an EntityManagerFactory for the persistence unit
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Student student = new Student("John", "Doe", "john.doe@example.com");
		em.persist(student);
		
		Student student2 = new Student("Sara", "Bianchi", "sara.bianchi@example.com");
		em.persist(student2);

		Teacher teacher = new Teacher("Mario", "Rossi", "mario.rossi@example.com");
		em.persist(teacher);

		Teacher teacher2 = new Teacher("Luigi", "Verdi", "luigi.verdi@example.com");
		em.persist(teacher2);

		Course course = new Course("SAM", "Software Architectures and Methodologies", teacher);
		em.persist(course);
		
		Course course2 = new Course("PT", "Penetration Testing", teacher2);
		em.persist(course2);

		Exam exam = new Exam(course, LocalDate.parse("2025-01-12"));
		em.persist(exam);
		
		Exam exam2 = new Exam(course2, LocalDate.parse("2025-01-23"));
		em.persist(exam2);
		
		Enrollment enrollment = new Enrollment(student, exam);
		em.persist(enrollment);
		
		Enrollment enrollment2 = new Enrollment(student2, exam2);
		em.persist(enrollment2);
		
		Enrollment enrollment3 = new Enrollment(student, exam2);
		em.persist(enrollment3);
		em.getTransaction().commit();
		
		TypedQuery<Student> allStudents = em.createQuery("Select s from Student s", Student.class);
		List<Student> resultList = allStudents.getResultList();
		System.out.println("List of students: " + resultList);
		
		TypedQuery<Teacher> teacherQuery = em.createQuery("Select t from Teacher t where surname =: teacherSurname", 
				Teacher.class);
		teacherQuery.setParameter("teacherSurname", "Verdi");
		Teacher result = teacherQuery.getSingleResult();
		System.out.println("Teacher Verdi: " + result);
		
		TypedQuery<Student> examStudents = em.createQuery(
				"Select en.student from Enrollment en JOIN en.exam ex JOIN ex.course c where c.name =: courseName", 
				Student.class);
		examStudents.setParameter("courseName", "SAM");
		resultList = examStudents.getResultList();
		System.out.println("Students registered at the SAM exam: " + resultList);
		
		
		examStudents.setParameter("courseName", "PT");
		resultList = examStudents.getResultList();
		System.out.println("Students registered at the PT exam: " + resultList);
		
		// Close the EntityManager
		em.close();
		emf.close();
	}

}
