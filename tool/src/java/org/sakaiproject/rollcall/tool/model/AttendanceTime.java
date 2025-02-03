package org.sakaiproject.rollcall.tool.model;

import java.time.LocalDateTime;

public class AttendanceTime {
    private Long id;
    private Long courseId;
    private Long studentId;
    private LocalDateTime attendanceTime;

    public AttendanceTime() {
    }

    public AttendanceTime(Long id, Long courseId, Long studentId, LocalDateTime attendanceTime) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.attendanceTime = attendanceTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }
}