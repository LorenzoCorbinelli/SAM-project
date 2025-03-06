package com.corbinelli.giamberini.examManagement.services;

import java.time.LocalDate;
import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.ExamDAO;
import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Exam;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ExamService {
	
	@Inject
	private ExamDAO examDAO;

	public ExamService() {}
	
	public void insertExam(Exam exam) {
		examDAO.save(exam);
	}
	
	public Exam insertExam(Course course, LocalDate date) {
		Exam exam = new Exam(course, date);
		examDAO.save(exam);
		return exam;
	}
	
	public Exam insertExam(Course course, String date) {
		Exam exam = new Exam(course, LocalDate.parse(date));
		examDAO.save(exam);
		return exam;
	}
	
	public Exam removeExam(Long id) {
		return examDAO.delete(id);
	}
	
	public Exam getExamInfoById(Long id) {
		return examDAO.findById(id);
	}
	
	public List<Exam> getAllExams() {
		return examDAO.findAll();
	}
	
	public List<LocalDate> getExamDatesOfCourse(Course course) {
		return examDAO.findExamDatesByCourse(course);
	}
	
	public List<Course> getExamCoursesInDate(LocalDate date) {
		return examDAO.findExamCoursesByDate(date);
	}
	
	public List<Course> getExamCoursesInDate(String date) {
		return examDAO.findExamCoursesByDate(LocalDate.parse(date));
	}
	
	public List<Exam> getExamsInAPeriod(LocalDate startDate, LocalDate endDate) {
		return examDAO.findExamsInAPeriod(startDate, endDate);
	}
	
	public List<Exam> getExamsInAPeriod(String startDate, String endDate) {
		return examDAO.findExamsInAPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate));
	}

}
