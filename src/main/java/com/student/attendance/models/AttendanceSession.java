package com.student.attendance.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance_sessions")
public class AttendanceSession {

	@Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private CoursesEntity course;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double geoRadiusMeters;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "initiator_id") // Lecturer or course rep
    private UsersEntity initiator;
    
    public AttendanceSession(CoursesEntity course, double latitude, double longitude, double geoRadiusMeters,
			LocalDateTime startTime, LocalDateTime endTime, UsersEntity initiator) {
		this.course = course;
		this.latitude = latitude;
		this.longitude = longitude;
		this.geoRadiusMeters = geoRadiusMeters;
		this.startTime = startTime;
		this.endTime = endTime;
		this.initiator = initiator;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public CoursesEntity getCourse() {
		return course;
	}

	public void setCourse(CoursesEntity course) {
		this.course = course;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getGeoRadiusMeters() {
		return geoRadiusMeters;
	}

	public void setGeoRadiusMeters(double geoRadiusMeters) {
		this.geoRadiusMeters = geoRadiusMeters;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public UsersEntity getInitiator() {
		return initiator;
	}

	public void setInitiator(UsersEntity initiator) {
		this.initiator = initiator;
	}

	public AttendanceSession() {
	}
}
