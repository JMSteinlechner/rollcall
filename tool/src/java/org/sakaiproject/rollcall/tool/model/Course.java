package org.sakaiproject.rollcall.tool.model;

import java.time.LocalDateTime;

public class Course {
    private Long id;
    private String courseId;
    private LocalDateTime start;
    private LocalDateTime end;

    public Course() {
    }

    public Course(Long id, String courseId, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.courseId = courseId;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}