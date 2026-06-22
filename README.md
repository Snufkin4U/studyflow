# StudyFlow Backend

![Java CI](https://github.com/Snufkin4U/studyflow/actions/workflows/ci.yml/badge.svg)

StudyFlow is a Spring Boot backend API for a smart academic planner system.

The project is part of a full-stack portfolio application designed to help students manage courses, academic tasks, deadlines, priorities, study workload, and course progress.

---

## Live API

Production Backend:

```text
https://studyflow-production-1e15.up.railway.app
```

Frontend Live Demo:

```text
https://studyflow-frontend-rust.vercel.app
```

Example API endpoints:

```text
https://studyflow-production-1e15.up.railway.app/api/courses
https://studyflow-production-1e15.up.railway.app/api/tasks
https://studyflow-production-1e15.up.railway.app/api/dashboard/summary
https://studyflow-production-1e15.up.railway.app/swagger-ui.html
```

---

## Project Overview

StudyFlow allows students to organize their academic work in one place.

The backend provides REST API endpoints for:

* Managing academic courses
* Managing study tasks
* Updating task status
* Filtering, searching, sorting, and paginating tasks
* Recommending urgent tasks
* Tracking overdue and upcoming tasks
* Calculating dashboard statistics
* Calculating progress per course
* Returning structured validation errors

---

## Technologies Used

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Maven
* H2 In-Memory Database
* Bean Validation
* Swagger / OpenAPI
* JUnit
* MockMvc
* GitHub Actions CI

---

## Main Features

### Course Management

The system supports full CRUD operations for academic courses.

Each course contains:

* ID
* Name
* Semester
* Difficulty

Supported operations:

* Create course
* Get all courses
* Get course by ID
* Update course
* Delete course

---

### Task Management

The system supports full CRUD operations for academic tasks.

Each task contains:

* ID
* Title
* Description
* Deadline
* Estimated hours
* Priority
* Status
* Related course

Supported task statuses:

* `TODO`
* `IN_PROGRESS`
* `DONE`

Supported operations:

* Create task
* Get all tasks
* Get task by ID
* Update task
* Update task status
* Delete task

---

### Task Filtering, Searching, Sorting and Pagination

The `GET /api/tasks` endpoint supports advanced query parameters.

| Parameter   | Description                     |
| ----------- | ------------------------------- |
| `status`    | Filter tasks by status          |
| `courseId`  | Filter tasks by course          |
| `search`    | Search tasks by text            |
| `sortBy`    | Sort tasks by selected field    |
| `direction` | Sort direction: `asc` or `desc` |
| `page`      | Page number                     |
| `size`      | Page size                       |

Example:

```http
GET /api/tasks?status=TODO&courseId=1&search=exam&sortBy=deadline&direction=asc&page=0&size=10
```

---

### Recommended Tasks

The backend includes a recommendation endpoint that ranks open tasks by urgency.

Endpoint:

```http
GET /api/tasks/recommended
```

The recommendation logic considers:

* Task priority
* Estimated hours
* Course difficulty
* Days left until deadline

Tasks marked as `DONE` are excluded from recommendations.

---

### Due Soon and Overdue Tasks

The backend provides endpoints for tracking upcoming and overdue academic work.

```http
GET /api/tasks/due-soon?days=30
```

```http
GET /api/tasks/overdue
```

These endpoints help students focus on tasks that require immediate attention.

---

### Dashboard

The backend provides dashboard data for the frontend.

Dashboard summary includes:

* Total courses
* Total tasks
* Open tasks
* Total estimated hours for open tasks

Course progress includes:

* Course ID
* Course name
* Total tasks
* Open tasks
* Done tasks
* Remaining estimated hours
* Completion percentage

---

## API Endpoints

### Dashboard Endpoints

| Method | Endpoint                 | Description                         |
| ------ | ------------------------ | ----------------------------------- |
| GET    | `/api/dashboard/summary` | Get overall dashboard summary       |
| GET    | `/api/dashboard/courses` | Get progress data grouped by course |

---

### Course Endpoints

| Method | Endpoint            | Description               |
| ------ | ------------------- | ------------------------- |
| GET    | `/api/courses`      | Get all courses           |
| GET    | `/api/courses/{id}` | Get course by ID          |
| POST   | `/api/courses`      | Create a new course       |
| PUT    | `/api/courses/{id}` | Update an existing course |
| DELETE | `/api/courses/{id}` | Delete a course           |

---

### Task Endpoints

| Method | Endpoint                             | Description                                                 |
| ------ | ------------------------------------ | ----------------------------------------------------------- |
| GET    | `/api/tasks`                         | Get tasks with filtering, searching, sorting and pagination |
| GET    | `/api/tasks/{id}`                    | Get task by ID                                              |
| POST   | `/api/tasks`                         | Create a new task                                           |
| PUT    | `/api/tasks/{id}`                    | Update an existing task                                     |
| PUT    | `/api/tasks/{id}/status?status=DONE` | Update task status                                          |
| DELETE | `/api/tasks/{id}`                    | Delete a task                                               |
| GET    | `/api/tasks/recommended`             | Get recommended tasks sorted by urgency                     |
| GET    | `/api/tasks/due-soon?days=30`        | Get open tasks due soon                                     |
| GET    | `/api/tasks/overdue`                 | Get overdue open tasks                                      |

---

## Example Course Request

```json
{
  "name": "Algorithms",
  "semester": "Year 2 - Semester B",
  "difficulty": 5
}
```

---

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

---

## Validation

The API uses Bean Validation to validate incoming requests.

When invalid input is sent, the backend returns a structured validation response.

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

This structure makes it easier for the frontend to display clear validation messages to the user.

---

## Database

The project uses an H2 in-memory database for local development.

The database is recreated when the application starts.

Important configuration:

```properties
spring.jpa.hibernate.ddl-auto=create
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

H2 Console:

```text
http://localhost:8080/h2-console
```

Example JDBC URL:

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

---

## Swagger API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

Swagger can be used to test all API endpoints directly from the browser.

---

## CORS

The backend is configured to allow requests from the local Vite React frontend.

Allowed local frontend origins:

```text
http://localhost:5173
http://localhost:5174
http://localhost:5175
```

---

## Running Locally

Clone the repository:

```bash
git clone https://github.com/Snufkin4U/studyflow.git
```

Enter the project folder:

```bash
cd studyflow
```

Run the backend:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

---

## Running Tests

Run all tests:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

The project includes integration tests using MockMvc.

---

## GitHub Actions CI

This repository includes a GitHub Actions workflow that runs automatically on push and pull request.

The CI pipeline runs:

* Maven build
* Backend tests

This helps verify that the backend remains stable after each change.

---

## Related Repository

Frontend repository:

```text
https://github.com/Snufkin4U/studyflow-frontend
```

---

## Current Project Status

Completed backend features:

* Course CRUD
* Task CRUD
* Task status update
* Task filtering
* Task search
* Task sorting
* Task pagination
* Recommended tasks endpoint
* Due soon tasks endpoint
* Overdue tasks endpoint
* Dashboard summary
* Course progress calculation
* Bean Validation
* Global exception handling
* H2 seed data
* Swagger / OpenAPI documentation
* Integration tests
* GitHub Actions CI

---

## Future Improvements

Possible future improvements:

* PostgreSQL integration
* User authentication
* Docker support
* Deployment to a cloud platform
* Multi-user support
* Advanced analytics for study workload

---

## Author

Maor Cohen

GitHub:

```text
https://github.com/Snufkin4U
```
