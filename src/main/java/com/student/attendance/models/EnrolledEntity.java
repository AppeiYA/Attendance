package com.student.attendance.models;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="enrolled")
public class EnrolledEntity {
	@Id 
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="student_id", nullable=false)
	private UsersEntity student;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id", nullable=false)
	private CoursesEntity course;

	public EnrolledEntity() {
	}

	public EnrolledEntity(UsersEntity student, CoursesEntity course) {
		this.student = student;
		this.course = course;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UsersEntity getStudent() {
		return student;
	}

	public void setStudent(UsersEntity student) {
		this.student = student;
	}

	public CoursesEntity getCourse() {
		return course;
	}

	public void setCourse(CoursesEntity course) {
		this.course = course;
	}
}
