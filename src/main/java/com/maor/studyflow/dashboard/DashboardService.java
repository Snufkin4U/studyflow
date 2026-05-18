package com.maor.studyflow.dashboard;

import com.maor.studyflow.course.Course;
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

    public List<CourseProgressResponse> getCourseProgress() {
        List<Course> courses = courseRepository.findAll();
        List<Task> tasks = taskRepository.findAll();

        return courses.stream()
                .map(course -> {
                    List<Task> courseTasks = tasks.stream()
                            .filter(task -> task.getCourse().getId().equals(course.getId()))
                            .toList();

                    long totalTasks = courseTasks.size();

                    long todoTasks = courseTasks.stream()
                            .filter(task -> task.getStatus() == TaskStatus.TODO)
                            .count();

                    long inProgressTasks = courseTasks.stream()
                            .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                            .count();

                    long doneTasks = courseTasks.stream()
                            .filter(task -> task.getStatus() == TaskStatus.DONE)
                            .count();

                    long openTasks = courseTasks.stream()
                            .filter(task -> task.getStatus() != TaskStatus.DONE)
                            .count();

                    double remainingEstimatedHours = courseTasks.stream()
                            .filter(task -> task.getStatus() != TaskStatus.DONE)
                            .mapToDouble(Task::getEstimatedHours)
                            .sum();

                    double completionPercentage = totalTasks == 0
                            ? 0
                            : (doneTasks * 100.0) / totalTasks;

                    return new CourseProgressResponse(
                            course.getId(),
                            course.getName(),
                            totalTasks,
                            todoTasks,
                            inProgressTasks,
                            doneTasks,
                            openTasks,
                            completionPercentage,
                            remainingEstimatedHours
                    );
                })
                .toList();
    }
}