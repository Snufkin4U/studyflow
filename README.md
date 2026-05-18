# StudyFlow

StudyFlow is a smart academic planner backend for students.  
It allows users to manage courses and study tasks, and includes a recommendation system that ranks tasks by urgency.

## Features

- Course management
- Task management
- H2 in-memory database
- Spring Data JPA repositories
- Relationship between tasks and courses
- Smart task recommendation logic
- Task status management: TODO, IN_PROGRESS, DONE

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## Main API Endpoints

### Courses

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/courses` | Get all courses |
| GET | `/api/courses/{id}` | Get course by ID |
| POST | `/api/courses` | Create a new course |
| PUT | `/api/courses/{id}` | Update a course |
| DELETE | `/api/courses/{id}` | Delete a course |

### Tasks

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tasks` | Get all tasks |
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks/recommended` | Get recommended tasks sorted by urgency |
| PUT | `/api/tasks/{id}/status?status=DONE` | Update task status |
    
## Recommendation Algorithm

The system calculates task urgency using:

```text
urgencyScore = priority * estimatedHours * courseDifficulty / daysLeft