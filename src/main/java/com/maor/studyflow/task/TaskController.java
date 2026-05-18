package com.maor.studyflow.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/recommended")
    public List<TaskRecommendationResponse> getRecommendedTasks() {
        return taskService.getRecommendedTasks();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status
    ) {
        Task task = taskService.updateTaskStatus(id, status);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }
}