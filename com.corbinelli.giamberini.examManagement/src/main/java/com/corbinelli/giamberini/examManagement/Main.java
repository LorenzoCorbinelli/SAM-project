package com.corbinelli.giamberini.examManagement;

import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		// Create an EntityManagerFactory for the persistence unit
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Student student = new Student("John", "Doe", "john.doe@example.com");
        em.persist(student);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        Teacher teacher = new Teacher("Mario", "Rossi", "mario.rossi@example.com");
        em.persist(teacher);
        em.getTransaction().commit();

        Student foundStudent = em.find(Student.class, student.getId());
        System.out.println("Found: " + foundStudent);
        
        Teacher foundTeacher = em.find(Teacher.class, teacher.getId());
        System.out.println("Found: " + foundTeacher);

        // Close the EntityManager
        em.close();
        emf.close();
	}

}
