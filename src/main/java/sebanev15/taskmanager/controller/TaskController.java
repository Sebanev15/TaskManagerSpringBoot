package sebanev15.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sebanev15.taskmanager.dto.TaskRequestDto;
import sebanev15.taskmanager.service.TaskService;

import java.util.Objects;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public String createTask(@RequestBody TaskRequestDto taskRequest) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal().toString();
        return taskService.createTask(taskRequest, email);
    }
}
