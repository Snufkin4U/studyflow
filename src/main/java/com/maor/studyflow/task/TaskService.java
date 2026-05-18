package com.maor.studyflow.task;

import com.maor.studyflow.course.Course;
import com.maor.studyflow.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(CreateTaskRequest request) {
        Course course = courseRepository.findById(request.getCourseId()).orElse(null);

        if (course == null) {
            return null;
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setEstimatedHours(request.getEstimatedHours());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus() == null ? TaskStatus.TODO : request.getStatus());
        task.setCourse(course);

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, CreateTaskRequest request) {
        Task existingTask = getTaskById(id);

        if (existingTask == null) {
            return null;
        }

        Course course = courseRepository.findById(request.getCourseId()).orElse(null);

        if (course == null) {
            return null;
        }

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setDeadline(request.getDeadline());
        existingTask.setEstimatedHours(request.getEstimatedHours());
        existingTask.setPriority(request.getPriority());
        existingTask.setStatus(request.getStatus() == null ? existingTask.getStatus() : request.getStatus());
        existingTask.setCourse(course);

        return taskRepository.save(existingTask);
    }

    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return null;
        }

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public List<TaskRecommendationResponse> getRecommendedTasks() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .map(task -> {
                    long daysLeft = calculateDaysLeft(task);
                    double urgencyScore = calculateUrgencyScore(task, daysLeft);

                    return new TaskRecommendationResponse(
                            task.getId(),
                            task.getTitle(),
                            task.getCourse().getName(),
                            task.getDeadline(),
                            task.getEstimatedHours(),
                            task.getPriority(),
                            task.getStatus(),
                            daysLeft,
                            urgencyScore
                    );
                })
                .sorted(Comparator.comparingDouble(TaskRecommendationResponse::getUrgencyScore).reversed())
                .toList();
    }

    private long calculateDaysLeft(Task task) {
        if (task.getDeadline() == null) {
            return 999;
        }

        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadline());

        if (daysLeft <= 0) {
            return 1;
        }

        return daysLeft;
    }

    private double calculateUrgencyScore(Task task, long daysLeft) {
        int courseDifficulty = task.getCourse().getDifficulty();

        return task.getPriority() * task.getEstimatedHours() * courseDifficulty / daysLeft;
    }

    public boolean deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }

        taskRepository.deleteById(id);
        return true;
    }
}