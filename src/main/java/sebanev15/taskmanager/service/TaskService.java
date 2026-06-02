package sebanev15.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sebanev15.taskmanager.dto.TaskRequestDto;
import sebanev15.taskmanager.dto.TaskResponseDto;
import sebanev15.taskmanager.dto.TaskStatusUpdateDto;
import sebanev15.taskmanager.mapper.TaskMapper;
import sebanev15.taskmanager.model.Task;
import sebanev15.taskmanager.model.User;
import sebanev15.taskmanager.repository.TaskRepository;
import sebanev15.taskmanager.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    public String createTask(TaskRequestDto taskRequest, String email) {
        User user = getAuthenticatedUser(email);
        Task task = taskMapper.toTask(taskRequest, user);

        taskRepository.save(task);
        return "Task created successfully: " + taskRequest.getTitle();
    }

    public List<TaskResponseDto> getTasksForUser(String email) {
        User user = getAuthenticatedUser(email);
        return taskMapper.toResponseDto(taskRepository.findByUser(user));
    }

    public String updateTask(Long id, TaskRequestDto taskRequest, String email) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        if(task.getUser().getEmail().equals(email)){
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setPriority(taskRequest.getPriority());
            task.setDueDate(taskRequest.getDueDate());
            task.setStatus(taskRequest.getStatus());
            taskRepository.save(task);
            return "Task updated successfully: " + id;
        }
        return "User does not have permission to update this task";
    }

    public String deleteTask(Long id, String email){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        if(task.getUser().getEmail().equals(email)){
            taskRepository.delete(task);
            return "Task deleted successfully: " + id;
        }
        return "User does not have permission to delete this task";
    }

    public String modifyTaskStatus(Long id, TaskStatusUpdateDto taskStatusUpdate, String email){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        if(task.getUser().getEmail().equals(email)) {
            task.setStatus(taskStatusUpdate.getStatus());
            taskRepository.save(task);
            return "Task status updated successfully: " + id;
        }
        return "User does not have permission to update this task";
    }

    private User getAuthenticatedUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
