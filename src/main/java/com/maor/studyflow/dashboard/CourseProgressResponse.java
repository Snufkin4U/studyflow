package com.maor.studyflow.dashboard;

public class CourseProgressResponse {

    private Long courseId;
    private String courseName;
    private long totalTasks;
    private long todoTasks;
    private long inProgressTasks;
    private long doneTasks;
    private long openTasks;
    private double completionPercentage;
    private double remainingEstimatedHours;

    public CourseProgressResponse(Long courseId, String courseName, long totalTasks,
                                  long todoTasks, long inProgressTasks, long doneTasks,
                                  long openTasks, double completionPercentage,
                                  double remainingEstimatedHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.totalTasks = totalTasks;
        this.todoTasks = todoTasks;
        this.inProgressTasks = inProgressTasks;
        this.doneTasks = doneTasks;
        this.openTasks = openTasks;
        this.completionPercentage = completionPercentage;
        this.remainingEstimatedHours = remainingEstimatedHours;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public long getTodoTasks() {
        return todoTasks;
    }

    public long getInProgressTasks() {
        return inProgressTasks;
    }

    public long getDoneTasks() {
        return doneTasks;
    }

    public long getOpenTasks() {
        return openTasks;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public double getRemainingEstimatedHours() {
        return remainingEstimatedHours;
    }
}