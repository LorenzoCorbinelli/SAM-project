package com.corbinelli.giamberini.examManagement.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "enrollments")
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Student student;
	@ManyToOne
	private Exam exam;
	
	public Enrollment() {}

	public Enrollment(Student student, Exam exam) {
		this.student = student;
		this.exam = exam;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Long getId() {
		return id;
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
