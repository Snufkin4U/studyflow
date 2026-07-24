# StudyFlow Backend

![Java CI](https://github.com/Snufkin4U/studyflow/actions/workflows/ci.yml/badge.svg)

StudyFlow Backend is a Spring Boot REST API for a smart academic planner system.

The project is part of a full-stack portfolio application designed to help students manage academic courses, study tasks, deadlines, priorities, study workload, smart task insights and course progress.

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

Swagger API Documentation:

```text
https://studyflow-production-1e15.up.railway.app/swagger-ui.html
```

Example API endpoints:

```text
https://studyflow-production-1e15.up.railway.app/api/courses
https://studyflow-production-1e15.up.railway.app/api/tasks
https://studyflow-production-1e15.up.railway.app/api/dashboard/summary
https://studyflow-production-1e15.up.railway.app/api/tasks/recommended
```

---

## Project Overview

StudyFlow allows students to organize their academic workload in one place.

The backend provides REST API endpoints for:

* Managing academic courses
* Managing study tasks
* Updating task status
* Filtering, searching, sorting and paginating tasks
* Recommending urgent tasks
* Tracking due soon and overdue tasks
* Calculating dashboard statistics
* Calculating progress per course
* Returning structured validation errors
* Supporting a deployed React frontend

---

## Technologies Used

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Maven
* H2 In-Memory Database for local development
* PostgreSQL for production
* Bean Validation
* Swagger / OpenAPI
* JUnit
* MockMvc
* GitHub Actions CI
* Railway deployment

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

Due soon tasks:

```http
GET /api/tasks/due-soon?days=30
```

Overdue tasks:

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

The project uses two database configurations:

* H2 in-memory database for local development
* PostgreSQL on Railway for production deployment

---

### Local Development Database

For local development, the project uses an H2 in-memory database.

The database is recreated when the application starts.

Important local configuration:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:h2:mem:studyflowdb;DB_CLOSE_DELAY=-1}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:sa}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}

spring.h2.console.enabled=${SPRING_H2_CONSOLE_ENABLED:true}
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=${SPRING_JPA_HIBERNATE_DDL_AUTO:create}
spring.jpa.defer-datasource-initialization=${SPRING_JPA_DEFER_DATASOURCE_INITIALIZATION:true}
spring.sql.init.mode=${SPRING_SQL_INIT_MODE:always}
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

### Production Database

In production, the backend uses PostgreSQL hosted on Railway.

The application supports environment-based database configuration, so it can use:

* H2 for local development
* PostgreSQL for production deployment

Production database configuration is provided through Railway environment variables:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=never
SPRING_JPA_DEFER_DATASOURCE_INITIALIZATION=false
SPRING_H2_CONSOLE_ENABLED=false
```

Production data persists after backend restarts and redeployments.

---

## Swagger API Documentation

Swagger UI is available locally at:

```text
http://localhost:8080/swagger-ui.html
```

Swagger UI is also available in production at:

```text
https://studyflow-production-1e15.up.railway.app/swagger-ui.html
```

Swagger can be used to test all API endpoints directly from the browser.

---

## CORS

The backend is configured to allow requests from the local Vite React frontend and the deployed Vercel frontend.

Allowed frontend origins include:

```text
http://localhost:5173
http://localhost:5174
http://localhost:5175
http://localhost:5176
https://studyflow-frontend-rust.vercel.app
```

The allowed origins can be configured with:

```properties
app.cors.allowed-origins=${APP_CORS_ALLOWED_ORIGINS:http://localhost:5173}
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

## Running the Full Stack with Docker

Docker Compose runs the complete StudyFlow system:

* React frontend served by Nginx
* Nginx reverse proxy
* Spring Boot backend
* PostgreSQL database
* Persistent PostgreSQL volume

### Prerequisites

* Docker Desktop
* Docker Compose

The backend and frontend repositories must be cloned into the same parent directory:

```text
Projects/
├── studyflow/
└── studyflow-frontend/
```

Clone both repositories:

```bash
git clone https://github.com/Snufkin4U/studyflow.git
git clone https://github.com/Snufkin4U/studyflow-frontend.git
```

Enter the backend directory:

```bash
cd studyflow
```

Create the local environment file from the provided template.

Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

Linux or macOS:

```bash
cp .env.example .env
```

Update `POSTGRES_PASSWORD` in `.env`, then build and start the system:

```bash
docker compose up -d --build
```

Check the running services:

```bash
docker compose ps
```

Application:

```text
http://localhost:5173
```

API through Nginx:

```text
http://localhost:5173/api/courses
```

Swagger through Nginx:

```text
http://localhost:5173/swagger-ui.html
```

View logs:

```bash
docker compose logs -f
```

Stop the system:

```bash
docker compose down
```

PostgreSQL data is stored in the `postgres_data` Docker volume and persists when containers are recreated.

To also delete the local database volume:

```bash
docker compose down -v
```

Warning: the `-v` option permanently deletes the local PostgreSQL data.

---

## Deployment

The backend is deployed on Railway.

Production backend:

```text
https://studyflow-production-1e15.up.railway.app
```

The production backend uses:

* Railway application service
* Railway PostgreSQL service
* Environment variables for database configuration
* PostgreSQL persistence across restarts

---

## Related Repository

Frontend repository:

```text
https://github.com/Snufkin4U/studyflow-frontend
```

Frontend live demo:

```text
https://studyflow-frontend-rust.vercel.app
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
* H2 seed data for local development
* PostgreSQL production database
* Production persistence after backend restart
* Swagger / OpenAPI documentation
* Integration tests
* GitHub Actions CI
* Railway deployment

---

## Future Improvements

Possible future improvements:

* User authentication and authorization
* Multi-user support
* Docker support
* Advanced analytics for study workload
* Calendar integration
* Task labels or categories
* End-to-end tests
* Custom domain

---

## Author

Maor Cohen

GitHub:

```text
https://github.com/Snufkin4U
```
