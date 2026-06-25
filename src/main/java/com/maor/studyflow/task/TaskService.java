package com.maor.studyflow.task;

import com.maor.studyflow.Category;
import com.maor.studyflow.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository,
                       CourseRepository courseRepository,
                       CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    public TaskPageResponse getTasks(
            TaskStatus status,
            Long courseId,
            Long categoryId,
            String search,
            String sortBy,
            String direction,
            int page,
            int size
    ) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        if (size > 50) {
            size = 50;
        }

        Comparator<Task> comparator = getTaskComparator(sortBy);

        if ("desc".equalsIgnoreCase(direction)) {
            comparator = comparator.reversed();
        }

        List<TaskResponse> filteredTasks = taskRepository.findAll()
                .stream()
                .filter(task -> status == null || task.getStatus() == status)
                .filter(task -> courseId == null || task.getCourse().getId().equals(courseId))
                .filter(task -> categoryId == null ||
                        (task.getCategory() != null && task.getCategory().getId().equals(categoryId)))
                .filter(task -> matchesSearch(task, search))
                .sorted(comparator)
                .map(this::mapToTaskResponse)
                .toList();

        long totalElements = filteredTasks.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        List<TaskResponse> content = filteredTasks.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();

        boolean first = page == 0;
        boolean last = totalPages == 0 || page >= totalPages - 1;

        return new TaskPageResponse(
                content,
                page,
                size,
                totalElements,
                totalPages,
                first,
                last
        );
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return null;
        }

        return mapToTaskResponse(task);
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        Course course = courseRepository.findById(request.getCourseId()).orElse(null);

        if (course == null) {
            return null;
        }

        Category category = getCategoryFromRequest(request);

        if (request.getCategoryId() != null && category == null) {
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
        task.setCategory(category);

        Task savedTask = taskRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    public TaskResponse updateTask(Long id, CreateTaskRequest request) {
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask == null) {
            return null;
        }

        Course course = courseRepository.findById(request.getCourseId()).orElse(null);

        if (course == null) {
            return null;
        }

        Category category = getCategoryFromRequest(request);

        if (request.getCategoryId() != null && category == null) {
            return null;
        }

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setDeadline(request.getDeadline());
        existingTask.setEstimatedHours(request.getEstimatedHours());
        existingTask.setPriority(request.getPriority());
        existingTask.setStatus(request.getStatus() == null ? existingTask.getStatus() : request.getStatus());
        existingTask.setCourse(course);
        existingTask.setCategory(category);

        Task savedTask = taskRepository.save(existingTask);

        return mapToTaskResponse(savedTask);
    }

    public TaskResponse updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return null;
        }

        task.setStatus(status);

        Task savedTask = taskRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    public boolean deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }

        taskRepository.deleteById(id);
        return true;
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

    public List<TaskResponse> getDueSoonTasks(int days) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);

        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .filter(task -> task.getDeadline() != null)
                .filter(task -> !task.getDeadline().isBefore(today))
                .filter(task -> !task.getDeadline().isAfter(endDate))
                .sorted(Comparator.comparing(Task::getDeadline))
                .map(this::mapToTaskResponse)
                .toList();
    }

    public List<TaskResponse> getOverdueTasks() {
        LocalDate today = LocalDate.now();

        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getStatus() != TaskStatus.DONE)
                .filter(task -> task.getDeadline() != null)
                .filter(task -> task.getDeadline().isBefore(today))
                .sorted(Comparator.comparing(Task::getDeadline))
                .map(this::mapToTaskResponse)
                .toList();
    }

    private TaskResponse mapToTaskResponse(Task task) {
        Course course = task.getCourse();
        Category category = task.getCategory();

        Long categoryId = category == null ? null : category.getId();
        String categoryName = category == null ? null : category.getName();
        String categoryColor = category == null ? null : category.getColor();

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getEstimatedHours(),
                task.getPriority(),
                task.getStatus(),
                course.getId(),
                course.getName(),
                categoryId,
                categoryName,
                categoryColor
        );
    }

    private Category getCategoryFromRequest(CreateTaskRequest request) {
        if (request.getCategoryId() == null) {
            return null;
        }

        return categoryRepository.findById(request.getCategoryId()).orElse(null);
    }

    private Comparator<Task> getTaskComparator(String sortBy) {
        if (sortBy == null) {
            return Comparator.comparing(Task::getDeadline);
        }

        return switch (sortBy) {
            case "priority" -> Comparator.comparing(Task::getPriority);
            case "estimatedHours" -> Comparator.comparing(Task::getEstimatedHours);
            case "status" -> Comparator.comparing(Task::getStatus);
            case "deadline" -> Comparator.comparing(Task::getDeadline);
            default -> Comparator.comparing(Task::getDeadline);
        };
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

    private boolean matchesSearch(Task task, String search) {
        if (search == null || search.isBlank()) {
            return true;
        }

        String normalizedSearch = search.toLowerCase();

        return containsIgnoreCase(task.getTitle(), normalizedSearch)
                || containsIgnoreCase(task.getDescription(), normalizedSearch)
                || containsIgnoreCase(task.getCourse().getName(), normalizedSearch)
                || (task.getCategory() != null && containsIgnoreCase(task.getCategory().getName(), normalizedSearch));
    }

    private boolean containsIgnoreCase(String value, String search) {
        return value != null && value.toLowerCase().contains(search);
    }
}