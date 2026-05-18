package com.maor.studyflow.task;

import java.time.LocalDate;

public class CreateTaskRequest {

    private String title;
    private String description;
    private LocalDate deadline;
    private double estimatedHours;
    private int priority;
    private TaskStatus status;
    private Long courseId;

    public CreateTaskRequest() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}