package sebanev15.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sebanev15.taskmanager.dto.TaskRequestDto;
import sebanev15.taskmanager.dto.TaskResponseDto;
import sebanev15.taskmanager.dto.TaskStatusUpdateDto;
import sebanev15.taskmanager.model.TaskPriority;
import sebanev15.taskmanager.model.TaskStatus;
import sebanev15.taskmanager.service.TaskService;


import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Create a new task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully: taskTitle"),
            @ApiResponse(responseCode = "400", description = "Invalid task data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized")
    })
    @PostMapping
    public String createTask(@RequestBody TaskRequestDto taskRequest) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.createTask(taskRequest, email);
    }

    @Operation(summary = "Get tasks for the authenticated user with optional filtering by status and priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page<TaskResponseDto>"),
            @ApiResponse(responseCode = "403", description = "Unauthorized")
    })
    @GetMapping
    public Page<TaskResponseDto> getTasks( Pageable pageable,
                                         @RequestParam(required = false) TaskStatus taskStatus,
                                         @RequestParam(required = false) TaskPriority taskPriority) {
        return taskService.getTasksForUser(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString(), taskStatus, taskPriority, pageable);
    }

    @Operation(summary = "Update an existing task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully: taskId"),
            @ApiResponse(responseCode = "400", description = "Invalid task data"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to update this task"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    public String updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskRequest){
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.updateTask(id, taskRequest, email);
    }

    @Operation(summary = "Delete an existing task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully: taskId"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to delete this task"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.deleteTask(id, email);
    }

    @Operation(summary = "Update the status of an existing task for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully: taskId"),
            @ApiResponse(responseCode = "400", description = "Invalid status value"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to modify this task"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PatchMapping("/{id}/status")
    public String modifyTaskStatus(@PathVariable Long id, @RequestBody TaskStatusUpdateDto request){
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.modifyTaskStatus(id, request, email);
    }

}
