package com.student.attendance.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.attendance.models.AttendanceSession;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, UUID> {

}
