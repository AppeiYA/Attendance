package com.student.attendance.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="courses")
public class CoursesEntity {	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="instructor_id")
	private UsersEntity instructor;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<EnrolledEntity> enrollments;
	
	@Column(nullable=false, length=255)
	private String name;
	
	@Column(nullable=false)
	private int units;
	
	@Column(nullable=false)
	private int semester;
	
	@Column(nullable=false)
	private boolean can_enroll;
	
	public CoursesEntity() {
	}

	public CoursesEntity(UsersEntity instructor, String name, int units, int semester, boolean can_enroll) {
		this.instructor = instructor;
		this.name = name;
		this.units = units;
		this.semester = semester;
		this.can_enroll = can_enroll;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UsersEntity getInstructor() {
		return instructor;
	}

	public void setInstructor(UsersEntity instructor) {
		this.instructor = instructor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public boolean isCan_enroll() {
		return can_enroll;
	}

	public void setCan_enroll(boolean can_enroll) {
		this.can_enroll = can_enroll;
	}
}
