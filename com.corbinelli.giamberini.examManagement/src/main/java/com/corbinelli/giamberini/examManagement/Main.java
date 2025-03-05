package com.corbinelli.giamberini.examManagement;



import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.model.Teacher;
import com.corbinelli.giamberini.examManagement.services.CourseService;
import com.corbinelli.giamberini.examManagement.services.EnrollmentService;
import com.corbinelli.giamberini.examManagement.services.ExamService;
import com.corbinelli.giamberini.examManagement.services.StudentService;
import com.corbinelli.giamberini.examManagement.services.TeacherService;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Main {
	
	public static void main(String[] args) {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		
		// Create instances for each service
		StudentService studentService = container.select(StudentService.class).get();
		TeacherService teacherService = container.select(TeacherService.class).get();
		CourseService courseService = container.select(CourseService.class).get();
		ExamService examService = container.select(ExamService.class).get();
		EnrollmentService enrollmentService = container.select(EnrollmentService.class).get();

		// Register 10 students
		Student biagio = studentService.registerStudent("Biagio", "Antonacci");
		Student marco = studentService.registerStudent("Marco", "Rossi");
		Student laura = studentService.registerStudent("Laura", "Bianchi");
		Student giovanni = studentService.registerStudent("Giovanni", "Verdi");
		Student francesca = studentService.registerStudent("Francesca", "Esposito");
		Student alessandro = studentService.registerStudent("Alessandro", "Ricci");
		Student elena = studentService.registerStudent("Elena", "Marini");
		Student davide = studentService.registerStudent("Davide", "Ferrari");
		Student valentina = studentService.registerStudent("Valentina", "Moretti");
		Student andrea = studentService.registerStudent("Andrea", "Gallo");

		// Register 5 teachers
		Teacher mario = teacherService.registerTeacher("Mario", "Rossi");
		Teacher luca = teacherService.registerTeacher("Luca", "Bianchi");
		Teacher sara = teacherService.registerTeacher("Sara", "Verdi");
		Teacher antonio = teacherService.registerTeacher("Antonio", "Esposito");
		Teacher giulia = teacherService.registerTeacher("Giulia", "Marini");

		// Assign at least 2 courses to each teacher
		Course pt = courseService.insertCourse("PT", "Penetration Testing", mario);
		Course sec = courseService.insertCourse("SEC", "Security Fundamentals", mario);

		Course db = courseService.insertCourse("DB", "Database Management", luca);
		Course sql = courseService.insertCourse("SQL", "Advanced SQL Queries", luca);

		Course ml = courseService.insertCourse("ML", "Machine Learning", sara);
		Course ai = courseService.insertCourse("AI", "Artificial Intelligence", sara);

		Course se = courseService.insertCourse("SE", "Software Engineering", antonio);
		Course algo = courseService.insertCourse("ALGO", "Algorithms and Data Structures", antonio);

		Course wd = courseService.insertCourse("WD", "Web Development", giulia);
		Course ux = courseService.insertCourse("UX", "User Experience Design", giulia);

		// Create at least 2 exams for each course
		Exam ptExam1 = examService.insertExam(pt, "2025-02-01");
		Exam ptExam2 = examService.insertExam(pt, "2025-02-15");

		Exam secExam1 = examService.insertExam(sec, "2025-02-05");
		Exam secExam2 = examService.insertExam(sec, "2025-02-20");

		Exam dbExam1 = examService.insertExam(db, "2025-02-08");
		Exam dbExam2 = examService.insertExam(db, "2025-02-22");

		Exam sqlExam1 = examService.insertExam(sql, "2025-02-12");
		Exam sqlExam2 = examService.insertExam(sql, "2025-02-25");

		Exam mlExam1 = examService.insertExam(ml, "2025-02-10");
		Exam mlExam2 = examService.insertExam(ml, "2025-02-24");

		Exam aiExam1 = examService.insertExam(ai, "2025-02-14");
		Exam aiExam2 = examService.insertExam(ai, "2025-02-28");

		Exam seExam1 = examService.insertExam(se, "2025-02-06");
		Exam seExam2 = examService.insertExam(se, "2025-02-21");

		Exam algoExam1 = examService.insertExam(algo, "2025-02-11");
		Exam algoExam2 = examService.insertExam(algo, "2025-02-27");

		Exam wdExam1 = examService.insertExam(wd, "2025-02-07");
		Exam wdExam2 = examService.insertExam(wd, "2025-02-23");

		Exam uxExam1 = examService.insertExam(ux, "2025-02-09");
		Exam uxExam2 = examService.insertExam(ux, "2025-02-26");


		// Enroll each student in at least 2 exams (mixed enrollments)
		enrollmentService.enroll(new Enrollment(biagio, mlExam1));
		enrollmentService.enroll(new Enrollment(biagio, uxExam2));

		enrollmentService.enroll(new Enrollment(marco, ptExam1));
		enrollmentService.enroll(new Enrollment(marco, sqlExam2));

		enrollmentService.enroll(new Enrollment(laura, wdExam1));
		enrollmentService.enroll(new Enrollment(laura, secExam1));

		enrollmentService.enroll(new Enrollment(giovanni, algoExam1));
		enrollmentService.enroll(new Enrollment(giovanni, dbExam2));

		enrollmentService.enroll(new Enrollment(francesca, aiExam2));
		enrollmentService.enroll(new Enrollment(francesca, seExam1));

		enrollmentService.enroll(new Enrollment(alessandro, mlExam2));
		enrollmentService.enroll(new Enrollment(alessandro, ptExam2));

		enrollmentService.enroll(new Enrollment(elena, uxExam1));
		enrollmentService.enroll(new Enrollment(elena, algoExam2));

		enrollmentService.enroll(new Enrollment(davide, sqlExam1));
		enrollmentService.enroll(new Enrollment(davide, wdExam2));

		enrollmentService.enroll(new Enrollment(valentina, secExam2));
		enrollmentService.enroll(new Enrollment(valentina, aiExam1));

		enrollmentService.enroll(new Enrollment(andrea, dbExam1));
		enrollmentService.enroll(new Enrollment(andrea, seExam2));

		System.out.println(studentService.getStudentInfoById((long)1));
		
		System.out.println(teacherService.getTeacherInfoById((long)2));
		
		System.out.println(courseService.getCoursesOfTeacher(giulia));
		System.out.println(courseService.getCoursesByName("AI"));
		System.out.println(courseService.getCoursesByPartialDescription("data"));
		
		System.out.println(examService.getExamsInAPeriod("2025-02-01", "2025-03-01"));
		System.out.println(examService.getExamDatesOfCourse(algo));
		
		System.out.println(enrollmentService.getExamsByStudent(elena));
		System.out.println(enrollmentService.getStudentsEnrolledForAnExam(mlExam2));
		System.out.println(enrollmentService.getTrialsForAnExamByStudentAndCourse(andrea, se));
				
		
		weld.shutdown();
	}

}
