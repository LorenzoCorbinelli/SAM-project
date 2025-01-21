package com.corbinelli.giamberini.examManagement.model;

import java.util.Objects;

public class Course {

	private Long id;
	private String name;
	private String description;
	private Teacher teacher;

	public Course(String name, String description, Teacher teacher) {
		this.name = name;
		this.description = description;
		this.teacher = teacher;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, teacher);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(teacher, other.teacher);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", description=" + description + ", teacher=" + teacher + "]";
	}
	
}
