package com.corbinelli.giamberini.examManagement.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exams")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Course course;
	private LocalDate date;

	public Exam(Course course, LocalDate date) {
		this.course = course;
		this.date = date;
	}

	void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, date, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exam other = (Exam) obj;
		return Objects.equals(course, other.course) && Objects.equals(date, other.date) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", course=" + course + ", date=" + date + "]";
	}
	
}
