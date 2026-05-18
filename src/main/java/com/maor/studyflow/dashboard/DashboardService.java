package com.maor.studyflow.dashboard;

import com.maor.studyflow.course.CourseRepository;
import com.maor.studyflow.task.Task;
import com.maor.studyflow.task.TaskRepository;
import com.maor.studyflow.task.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;

    public DashboardService(CourseRepository courseRepository, TaskRepository taskRepository) {
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
    }

    public DashboardSummaryResponse getSummary() {
        List<Task> tasks = taskRepository.findAll();

        long totalCourses = courseRepository.count();
        long totalTasks = tasks.size();

        long todoTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.TODO)
                .count();

        long inProgressTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .count();

        long doneTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .count();

        long openTasks = tasks.stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .count();

        double totalEstimatedHoursForOpenTasks = tasks.stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .mapToDouble(Task::getEstimatedHours)
                .sum();

        return new DashboardSummaryResponse(
                totalCourses,
                totalTasks,
                todoTasks,
                inProgressTasks,
                doneTasks,
                openTasks,
                totalEstimatedHoursForOpenTasks
        );
    }
}