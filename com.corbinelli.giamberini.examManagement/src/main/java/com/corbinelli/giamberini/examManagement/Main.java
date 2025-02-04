package com.corbinelli.giamberini.examManagement;



import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.model.Teacher;
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
		Student sara = studentService.registerStudent("Sara", "Antonacci");
		Student biagio2 = studentService.registerStudent("Biagio", "Antonacci");
		Student biagio3 = studentService.registerStudent("Biagio", "Antonacci");
		Student biagio4 = studentService.registerStudent("Biagio", "Rossi");
		Student veronica = studentService.registerStudent("Veronica", "Derilli");
		
		System.out.println(biagio);
		System.out.println(sara);
		System.out.println(biagio2);
		System.out.println(biagio3);
		System.out.println(biagio4);
		System.out.println(veronica);
		
		TeacherService teacherService = container.select(TeacherService.class).get();
		Teacher teacher = teacherService.registerTeacher("Mario", "Rossi");
		Teacher teacher2 = teacherService.registerTeacher("Mario", "Rossi");
		Teacher teacher3 = teacherService.registerTeacher("Luigi", "Rossi");
		Teacher teacher4 = teacherService.registerTeacher("Mario", "Verdi");
		
		System.out.println(teacher);
		System.out.println(teacher2);
		System.out.println(teacher3);
		System.out.println(teacher4);
		
		weld.shutdown();
	}

}
