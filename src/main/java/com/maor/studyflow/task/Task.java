package com.maor.studyflow.task;

import com.maor.studyflow.course.Course;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate deadline;
    private double estimatedHours;
    private int priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Task() {
    }

    public Task(Long id, String title, String description, LocalDate deadline,
                double estimatedHours, int priority, TaskStatus status, Course course) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
        this.priority = priority;
        this.status = status;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCourse(Course course) {
        this.course = course;
    }
}