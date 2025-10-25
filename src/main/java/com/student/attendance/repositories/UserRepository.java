package com.student.attendance.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.attendance.models.UsersEntity;

public interface UserRepository extends JpaRepository<UsersEntity, UUID> {

}
