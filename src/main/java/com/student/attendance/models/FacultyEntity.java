package com.student.attendance.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name="faculties")
public class FacultyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(nullable=false, length=255)
	private String name;
	
	@OneToMany(
			mappedBy="faculty",
			cascade=CascadeType.ALL,
			fetch=FetchType.LAZY
			)
	@JsonManagedReference
	private List<DepartmentEntity> departments;
	
	public FacultyEntity(String name) {
		this.name = name;
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
	
	
}
