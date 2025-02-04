package com.corbinelli.giamberini.examManagement;


import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.services.StudentService;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Main {
	
	public static void main(String[] args) {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		
		StudentService studentService = container.select(StudentService.class).get();
		
//		Student student1 = new Student("Biagio", "Antonacci", "biagio.antonacci@example.com");
//		Student student2 = new Student("Sara", "Cartocci", "sara.cartocci@example.com");
//		Student student3 = new Student("Matteo", "Bianchi", "matteo.biacnhi@example.com");
		
		Student biagio = studentService.registerStudent("Biagio", "Antonacci");
		Student sara = studentService.registerStudent("Sara", "Cartocci");
		Student matteo = studentService.registerStudent("Matteo", "Bianchi"); 
		Student veronica = studentService.registerStudent("Veronica", "Derilli");
		
		System.out.print(biagio.toString());
		
	}

}
