package com.student.attendance.services;

import org.springframework.stereotype.Service;

import com.student.attendance.dtos.UserRegisterDto;
import com.student.attendance.models.DepartmentEntity;
import com.student.attendance.models.UsersEntity;

@Service
public class UsersMapper {
//	from userRegisterDto to user entity
	public UsersEntity toUser(UserRegisterDto dto) {
		var user = new UsersEntity();
		user.setFirst_name(dto.first_name());
		user.setLast_name(dto.last_name());
		user.setMatric_no(dto.matric_no());
		user.setEmail(dto.email());
		user.setRole(dto.role());
		user.setPassword(dto.password());
		
		var department = new DepartmentEntity();
		department.setId(dto.department_id());
		
		user.setDepartment(department);
		
		return user;
	}
}
