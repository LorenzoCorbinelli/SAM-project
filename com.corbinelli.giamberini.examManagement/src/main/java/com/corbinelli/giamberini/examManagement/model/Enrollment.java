package com.corbinelli.giamberini.examManagement.model;

import java.util.Objects;

public class Enrollment {
	
	private Student student;
	private Exam exam;

	public Enrollment(Student student, Exam exam) {
		this.student = student;
		this.exam = exam;
	}

	public Student getStudent() {
		return student;
	}

	public Exam getExam() {
		return exam;
	}

	@Override
	public int hashCode() {
		return Objects.hash(exam, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enrollment other = (Enrollment) obj;
		return Objects.equals(exam, other.exam) && Objects.equals(student, other.student);
	}

	@Override
	public String toString() {
		return "Enrollment [student=" + student + ", exam=" + exam + "]";
	}

}
