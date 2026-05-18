package com.maor.studyflow.task;

import java.time.LocalDate;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private double estimatedHours;
    private int priority;
    private TaskStatus status;
    private Long courseId;
    private String courseName;

    public TaskResponse(Long id, String title, String description, LocalDate deadline,
                        double estimatedHours, int priority, TaskStatus status,
                        Long courseId, String courseName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
        this.priority = priority;
        this.status = status;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public int getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}