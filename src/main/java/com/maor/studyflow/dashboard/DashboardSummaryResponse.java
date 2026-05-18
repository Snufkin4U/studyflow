package com.maor.studyflow.dashboard;

public class DashboardSummaryResponse {

    private long totalCourses;
    private long totalTasks;
    private long todoTasks;
    private long inProgressTasks;
    private long doneTasks;
    private long openTasks;
    private double totalEstimatedHoursForOpenTasks;

    public DashboardSummaryResponse(long totalCourses, long totalTasks, long todoTasks,
                                    long inProgressTasks, long doneTasks, long openTasks,
                                    double totalEstimatedHoursForOpenTasks) {
        this.totalCourses = totalCourses;
        this.totalTasks = totalTasks;
        this.todoTasks = todoTasks;
        this.inProgressTasks = inProgressTasks;
        this.doneTasks = doneTasks;
        this.openTasks = openTasks;
        this.totalEstimatedHoursForOpenTasks = totalEstimatedHoursForOpenTasks;
    }

    public long getTotalCourses() {
        return totalCourses;
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

    public double getTotalEstimatedHoursForOpenTasks() {
        return totalEstimatedHoursForOpenTasks;
    }
}