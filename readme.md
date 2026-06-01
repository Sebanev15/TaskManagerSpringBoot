# Task Manager API

REST API for task management built with Spring Boot.

## Tech Stack

- Java 21
- Spring Boot 4.0.6
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Docker + Docker Compose
- JUnit 5 + Mockito
- Swagger / OpenAPI

## Status

🚧 In progress — following a structured 5-week development plan.

## Features

### Done

- User registration with JWT backend structure
- User login with JWT token generation
- PostgreSQL persistence
- Dockerized local database setup
- Create tasks

### Planned

- update, delete and filter tasks
- Pagination and status filtering
- Full API documentation via Swagger UI

## Possible future features

- Task comments and attachments
- Task sharing and collaboration
- Notifications and reminders
- User roles and permissions

## Project Structure

- `src/main/java/sebanev15/taskmanager/controller` — REST controllers
- `src/main/java/sebanev15/taskmanager/dto` — data transfer objects
- `src/main/java/sebanev15/taskmanager/mapper` — mapping between entities and DTOs
- `src/main/java/sebanev15/taskmanager/model` — entity classes
- `src/main/java/sebanev15/taskmanager/repository` — data access layer
- `src/main/java/sebanev15/taskmanager/security` — security configuration and JWT utilities
- `src/main/java/sebanev15/taskmanager/service` — business logic

## Domain Model

### User

- `id`
- `name`
- `email`
- `password`
- `created_at`
- `tasks` — list of owned tasks

### Task

- `id`
- `title`
- `description`
- `status` (`TODO`, `IN_PROGRESS`, `DONE`)
- `priority` (`LOW`, `MEDIUM`, `HIGH`)
- `created_at`
- `due_date`
- `user_id` — owner

## Controllers

### AuthController

#### Register

- **Endpoint:** `POST /auth/register`

**Request body**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Current response**

- Returns a plain string message

**Example**

```text
User registered successfully: John Doe
```

**If the email already exists**

```text
Email already exists
```

#### Login

- **Endpoint:** `POST /auth/login`

**Request body**

```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

**Current response**

- Returns the JWT as a plain string

**Example**

```text
eyJhbGciOiJIUzI1NiJ9...
```

**Invalid credentials**

```text
Invalid credentials
```

**User not found**

```text
User not found
```

### TaskController

#### Create Task
- **Endpoint:** `POST /tasks/create`

**Request body**

```json
{
  "title": "Finish project",
  "description": "Complete the task manager API",
  "priority": "HIGH",
  "dueDate": "2026-07-01"
}
```
*Notes*: 
- `status` is not included in the request body as it defaults to `TODO` when creating a new task.
- The `dueDate` field is optional; if not provided, it will be set to `null`.
- The `user_id` is not included in the request body as it is determined from the authenticated user context. If the user is not authenticated, the request will be rejected.

**Current response**
- Returns a plain string message with the created task's title
- Example

```text
Task created successfully: Finish project
```

**Invalid credentials and invalid token**

It not returns a message, but the request will be rejected with a 403 Forbidden status code if the user is not authenticated or if it missed a necessary camp.

## Example curl requests

### Register

```powershell
curl -X POST http://localhost:8081/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"password\":\"secret123\"}"
```

### Login

```powershell
curl -X POST http://localhost:8081/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"john@example.com\",\"password\":\"secret123\"}"
```

### Create Task

```powershellcurl -X POST http://localhost:8081/tasks/create ^
  -H "Content-Type: application/json" ^
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." ^
  -d "{\"title\":\"Finish project\",\"description\":\"Complete the task manager API\",\"priority\":\"HIGH\",\"dueDate\":\"2026-07-01\"}"
```

## Getting Started

### Prerequisites

- Java 21
- Docker Desktop

### Run locally on Windows / PowerShell

1. Clone the repository:

   ```powershell
   git clone https://github.com/Sebanev15/TaskManagerSpringBoot.git
   cd TaskManagerSpringBoot
   ```

2. Start the database:

   ```powershell
   docker compose up -d
   ```

3. Run the application:

   ```powershell
   .\mvnw spring-boot:run
   ```

4. Open the app:

   ```text
   http://localhost:8081
   ```

## Database Configuration

Current local configuration:

- **DB name:** `taskmanagerdb`
- **DB user:** `taskuser`
- **DB password:** `taskpass123`
- **JDBC URL:** `jdbc:postgresql://localhost:5433/taskmanagerdb`

These values match:

- `docker-compose.yml`
- `src/main/resources/application.yml`
