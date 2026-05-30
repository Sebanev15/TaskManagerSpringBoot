# Task Manager API
  
  REST API for task management built with Spring Boot.
  
  ## Tech Stack
  - Java 21 + Spring Boot 4.0.6
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
  
  ## Possible future features
  - Task comments and attachments
  - Task sharing and collaboration
  - Notifications and reminders
  - User roles and permissions
  
  ## Structure
  - `src/main/java/sebanev15/taskmanager/controller` — REST controllers
  - `src/main/java/sebanev15/taskmanager/dto` — data transfer objects
  - `src/main/java/sebanev15/taskmanager/mapper` — mapping between entities and DTOs
  - `src/main/java/sebanev15/taskmanager/model` — entity classes
  - `src/main/java/sebanev15/taskmanager/repository` — data access layer
  - `src/main/java/sebanev15/taskmanager/security` — security configuration and JWT utilities
  - `src/main/java/sebanev15/taskmanager/service` — business logic
  
  ## Model
  - User: id, name, email, password, created_at, tasks (list of owned tasks)
  - Task: id, title, description, status (TODO, IN_PROGRESS, DONE), priority (LOW, MEDIUM, HIGH), created_at, due_date, user_id(owner)
  
  ## Controllers
  
  ### AuthController
  
  #### Register
  - Endpoint: POST /api/auth/register
  - Request body example:
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com",
    "password": "secret123"
  }
  ```
- Successful response example (IN FUTURE, currently only return the name of the user created):
  ```json
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "createdAt": "2026-05-30T16:49:40Z",
    "tasks": []
  }
  ```
  - Error response example (IN FUTURE, currently return a string message):
  ```json
  {
    "timestamp": "2026-05-30T16:50:00Z",
    "status": 400,
    "errors": ["Email is invalid", "Password must be at least 6 chars"]
  }
  ```
  
  #### Login
  - Endpoint: POST /api/auth/login
  - Request body example:
  ```json
  {
    "email": "john@example.com",
    "password": "secret123"
  }
  ```
  - Successful response (IN FUTURE, currently return empty body):
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "expiresIn": 3600
  }
  ```
  - Invalid credentials: error message
  
  ### Example curl requests
  Register:
  ```bash
  curl -X POST http://localhost:8081/api/auth/register \
    -H "Content-Type: application/json" \
    -d '{"name":"John Doe","email":"john@example.com","password":"secret123"}'
  ```
  
  Login:
  ```bash
  curl -X POST http://localhost:8081/api/auth/login \
    -H "Content-Type: application/json" \
    -d '{"email":"john@example.com","password":"secret123"}'
  ```
  
  ## Getting Started
  
  ### Prerequisites
  - Java 21
  - Docker Desktop
  
  ### Run locally (Windows / PowerShell)
  1. Clone the repository:
  ```powershell
  git clone https://github.com/Sebanev15/TaskManagerSpringBoot.git
  ```
  2. Start the database:
  ```powershell
  cd <ruta-del-proyecto>
  docker compose up -d
  ```
  3. Run the application from IntelliJ or with:
  ```powershell
  .\mvnw spring-boot:run
  ```
  4. App available at `http://localhost:8081`
  
  ## Notes on DB configuration and troubleshooting
  
  - Default DB settings used in this repo (ver `docker-compose.yml` y `src/main/resources/application.yml`):
    - DB name: `taskmanagerdb`
    - DB user: `taskuser`
    - DB password: `taskpass123`
    - JDBC URL (app): `jdbc:postgresql://localhost:5433/taskmanagerdb` (si en `docker-compose.yml` se mapearon los puertos como `5433:5432`)
  
  - Si recibes "password authentication failed for user 'X'":
    - Asegúrate de que `application.yml` y `docker-compose.yml` usan las mismas credenciales.
    - Si cambias credenciales en `docker-compose.yml` pero ya existe un volumen con datos, recrea el volumen (¡esto borra datos!):
    ```powershell
    docker compose down -v
    docker compose up -d
    ```
  
  - Si el puerto 8080 está ocupado (la app usa 8081 por defecto aquí), identifica y mata el proceso en Windows:
  ```powershell
  netstat -ano | findstr :8080
  # toma el PID y
  taskkill /PID <PID> /F
  # o
  Stop-Process -Id <PID> -Force
  ```
  
  - Para ver logs del contenedor Postgres:
  ```powershell
  docker logs -f taskmanager-db
  ```