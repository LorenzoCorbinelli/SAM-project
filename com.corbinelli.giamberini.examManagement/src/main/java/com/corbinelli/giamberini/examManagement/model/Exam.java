package com.corbinelli.giamberini.examManagement.model;

import java.util.Date;
import java.util.Objects;

public class Exam {

	private Long id;
	private Course course;
	private Date date;

	public Exam(Course course, Date date) {
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

	public Date getDate() {
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
