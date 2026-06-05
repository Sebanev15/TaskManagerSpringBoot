package sebanev15.taskmanager.controller;

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

    @PostMapping
    public String createTask(@RequestBody TaskRequestDto taskRequest) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.createTask(taskRequest, email);
    }

    @GetMapping
    public Page<TaskResponseDto> getTasks( Pageable pageable,
                                         @RequestParam(required = false) TaskStatus taskStatus,
                                         @RequestParam(required = false) TaskPriority taskPriority) {
        return taskService.getTasksForUser(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString(), taskStatus, taskPriority, pageable);
    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskRequest){
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.updateTask(id, taskRequest, email);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.deleteTask(id, email);
    }

    @PatchMapping("/{id}/status")
    public String modifyTaskStatus(@PathVariable Long id, @RequestBody TaskStatusUpdateDto request){
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.modifyTaskStatus(id, request, email);
    }

}
