package com.student.attendance.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name="departments")
public class DepartmentEntity {

	public DepartmentEntity() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(length=255, nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="faculty_id")
	@JsonIgnoreProperties({"departments"})
	private FacultyEntity faculty;
	
	@OneToMany(
			mappedBy="department", 
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY
			)
	@JsonManagedReference
	private List<UsersEntity> users;

	public DepartmentEntity(String name, FacultyEntity faculty) {
		this.name = name;
		this.faculty = faculty;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FacultyEntity getFaculty() {
		return faculty;
	}

	public void setFaculty(FacultyEntity faculty) {
		this.faculty = faculty;
	}
	
}
