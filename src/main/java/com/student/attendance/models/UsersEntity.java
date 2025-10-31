package com.student.attendance.models;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class UsersEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(nullable=false, length=20)
	private String first_name;
	
	@Column(nullable=false, length=20)
	private String last_name;
	
	@Column(nullable=false, unique=true)
	private Long matric_no;
	
	public enum UserRole {student, instructor}
	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(columnDefinition="user_role")
	private UserRole role;
	
	public UsersEntity() {
	}

	@ManyToOne
	@JoinColumn(name="department_id")
	@JsonIgnoreProperties({"users"})
	private DepartmentEntity department;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EnrolledEntity> enrollments;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@Column(nullable=false)
	private String password;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Long getMatric_no() {
		return matric_no;
	}

	public void setMatric_no(Long matric_no) {
		this.matric_no = matric_no;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsersEntity(String first_name, String last_name, Long matric_no, UserRole role, UUID department_id,
			String email, String password) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.matric_no = matric_no;
		this.role = role;
		this.email = email;
		this.password = password;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}
}
