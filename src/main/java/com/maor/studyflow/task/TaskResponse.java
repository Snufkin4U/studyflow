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
    private Long categoryId;
    private String categoryName;
    private String categoryColor;

    public TaskResponse(Long id, String title, String description, LocalDate deadline,
                        double estimatedHours, int priority, TaskStatus status,
                        Long courseId, String courseName,
                        Long categoryId, String categoryName, String categoryColor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
        this.priority = priority;
        this.status = status;
        this.courseId = courseId;
        this.courseName = courseName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryColor() {
        return categoryColor;
    }
}