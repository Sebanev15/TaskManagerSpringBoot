# Task Manager API

REST API for task management built with Spring Boot.

## Tech Stack
- Java 21 + Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Docker + Docker Compose
- JUnit 5 + Mockito
- Swagger / OpenAPI

## Status
🚧 In progress — following a structured 5-week development plan.

## Features (planned)
- User registration and login with JWT authentication
- Create, update, delete and filter tasks
- Pagination and status filtering
- Full API documentation via Swagger UI

## Getting Started

### Prerequisites
- Java 21
- Docker Desktop

### Run locally
1. Clone the repository
```bash
   git clone https://github.com/Sebanev15/TaskManagerSpringBoot.git
```
2. Start the database
```bash
   docker compose up -d
```
3. Run the application from IntelliJ or with:
```bash
   ./mvnw spring-boot:run
```
4. App available at `http://localhost:8081`