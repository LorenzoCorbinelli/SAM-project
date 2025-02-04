package com.corbinelli.giamberini.examManagement;


import java.time.LocalDate;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.model.Teacher;
import com.corbinelli.giamberini.examManagement.services.CourseService;
import com.corbinelli.giamberini.examManagement.services.ExamService;
import com.corbinelli.giamberini.examManagement.services.StudentService;
import com.corbinelli.giamberini.examManagement.services.TeacherService;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Main {
	
	public static void main(String[] args) {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		
		StudentService studentService = container.select(StudentService.class).get();
		
		Student biagio = studentService.registerStudent("Biagio", "Antonacci");
		studentService.registerStudent("Sara", "Cartocci");
		studentService.registerStudent("Matteo", "Bianchi"); 
		studentService.registerStudent("Veronica", "Derilli");
		
		System.out.print(biagio.toString());
		
		TeacherService teacherService = container.select(TeacherService.class).get();
		Teacher teacher = new Teacher("Mario", "Rossi", "mario.rossi@example.com");
		teacherService.registerTeacher(teacher);
		
		CourseService courseService = container.select(CourseService.class).get();
		Course course = new Course("PT", "Penetration testing", teacher);
		courseService.insertCourse(course);
		System.out.println(courseService.getAllCourses());
		
		ExamService examService = container.select(ExamService.class).get();
		Exam exam = new Exam(course, LocalDate.parse("2025-01-12"));
		examService.insertExam(exam);
		System.out.println(examService.getExamCoursesInDate("2025-01-12"));
		
		weld.shutdown();
	}

}
