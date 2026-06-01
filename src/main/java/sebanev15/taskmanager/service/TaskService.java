package sebanev15.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sebanev15.taskmanager.dto.TaskRequestDto;
import sebanev15.taskmanager.mapper.TaskMapper;
import sebanev15.taskmanager.model.Task;
import sebanev15.taskmanager.model.User;
import sebanev15.taskmanager.repository.TaskRepository;
import sebanev15.taskmanager.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    public String createTask(TaskRequestDto taskRequest, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Task task = taskMapper.toTask(taskRequest, user);

        taskRepository.save(task);
        return "Task created successfully: " + taskRequest.getTitle();
    }
}
