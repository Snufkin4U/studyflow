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
- Input validation for courses and tasks
- Structured validation error responses

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## Main API Endpoints

### Dashboard

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/dashboard/summary` | Get overall study dashboard summary |
| GET | `/api/dashboard/courses` | Get task progress grouped by course |

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
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/api/tasks/recommended` | Get recommended tasks sorted by urgency |
| PUT | `/api/tasks/{id}/status?status=DONE` | Update task status |

## Recommendation Algorithm

The system calculates task urgency using:

```text
urgencyScore = priority * estimatedHours * courseDifficulty / daysLeft
```

Tasks with higher urgency scores appear first.

Tasks marked as `DONE` are excluded from recommendations.

## Example Course Request

```json
{
  "name": "Algorithms",
  "semester": "Year 2 - Semester B",
  "difficulty": 5
}
```

## Example Task Request

```json
{
  "title": "Solve DP exercises",
  "description": "Practice dynamic programming before exam",
  "deadline": "2026-06-01",
  "estimatedHours": 8,
  "priority": 5,
  "status": "TODO",
  "courseId": 1
}
```

## Validation Error Example

When invalid input is sent to the API, the server returns a structured validation response.

Example invalid course request:

```json
{
  "name": "",
  "semester": "",
  "difficulty": 10
}
```

Example response:

```json
{
  "status": 400,
  "error": "Validation failed",
  "messages": {
    "difficulty": "Difficulty must be at most 5",
    "name": "Course name is required",
    "semester": "Semester is required"
  }
}
```

## How to Run

Clone the project:

```bash
git clone https://github.com/Snufkin4U/studyflow.git
```

Enter the project folder:

```bash
cd studyflow
```

Run with Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The server will run at:

```text
http://localhost:8080
```

## H2 Console

H2 Console is available at:

```text
http://localhost:8080/h2-console
```

Use the following JDBC URL:

```text
jdbc:h2:mem:studyflowdb
```

Default username:

```text
sa
```

Password:

```text
leave empty
```

## Project Status

Backend MVP is in progress.

Completed:

- Course CRUD
- Task CRUD
- Task recommendation algorithm
- Input validation
- Structured validation error responses
- H2 database integration

Planned features:

- PostgreSQL integration
- React frontend
- User authentication
- Docker support
- Unit tests