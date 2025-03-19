package com.corbinelli.giamberini.examManagement.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Lob
	private String description;
	@ManyToOne
	private Teacher teacher;
	
	public Course() {}

	public Course(String name, String description, Teacher teacher) {
		this.name = name;
		this.description = description;
		this.teacher = teacher;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
		return (id != null) ? Objects.hash(id) : System.identityHashCode(this);
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
		if (this.id == null || other.id == null) {
			return super.equals(obj);
		}
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", description=" + description + ", teacher=" + teacher + "]";
	}
	
}
