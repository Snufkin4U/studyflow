package com.maor.studyflow.task;

import java.time.LocalDate;

public class TaskRecommendationResponse {

    private Long taskId;
    private String title;
    private String courseName;
    private LocalDate deadline;
    private double estimatedHours;
    private int priority;
    private TaskStatus status;
    private long daysLeft;
    private double urgencyScore;

    public TaskRecommendationResponse(Long taskId, String title, String courseName,
                                      LocalDate deadline, double estimatedHours,
                                      int priority, TaskStatus status,
                                      long daysLeft, double urgencyScore) {
        this.taskId = taskId;
        this.title = title;
        this.courseName = courseName;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
        this.priority = priority;
        this.status = status;
        this.daysLeft = daysLeft;
        this.urgencyScore = urgencyScore;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
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

    public long getDaysLeft() {
        return daysLeft;
    }

    public double getUrgencyScore() {
        return urgencyScore;
    }
}