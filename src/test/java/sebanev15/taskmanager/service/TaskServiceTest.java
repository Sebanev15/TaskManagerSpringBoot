package sebanev15.taskmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sebanev15.taskmanager.dto.LoginRequestDto;
import sebanev15.taskmanager.dto.TaskRequestDto;
import sebanev15.taskmanager.dto.TaskStatusUpdateDto;
import sebanev15.taskmanager.exception.ResourceNotFoundException;
import sebanev15.taskmanager.exception.UnauthorizedException;
import sebanev15.taskmanager.mapper.TaskMapper;
import sebanev15.taskmanager.model.Task;
import sebanev15.taskmanager.model.TaskPriority;
import sebanev15.taskmanager.model.TaskStatus;
import sebanev15.taskmanager.model.User;
import sebanev15.taskmanager.repository.TaskRepository;
import sebanev15.taskmanager.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;
    private User user = new User();
    private Task task = new Task();
    private LoginRequestDto loginRequest = new LoginRequestDto();
    private TaskRequestDto taskRequest = new TaskRequestDto();
    private TaskStatusUpdateDto taskStatusUpdate = new TaskStatusUpdateDto();

    @BeforeEach
    public void setUp() {
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPassword("password");

        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("password");

        task.setId(1L);
        task.setTitle("Test Task");
        task.setUser(user);
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);

        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setStatus(TaskStatus.TODO);
        taskRequest.setPriority(TaskPriority.HIGH);

        taskStatusUpdate.setStatus(TaskStatus.IN_PROGRESS);
    }

    @Test
    public void createTaskWithNotExistingUser(){
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.createTask(taskRequest, "test@gmail.com");
        });
    }

    @Test
    public void createTaskWithExistingUser(){
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(taskMapper.toTask(taskRequest, user)).thenReturn(task);

        taskService.createTask(taskRequest, "test@gmail.com");

        verify(taskRepository).save(task);
    }

    @Test
    public void updateTaskWithNotExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.updateTask(1L, taskRequest, "test@gmail.com");
        });
    }

    @Test
    public void updateTaskWithUnauthorizedUser(){
        User otherUser = new User();
        otherUser.setEmail("other@gmail.com");
        task.setUser(otherUser);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(UnauthorizedException.class, () ->
                taskService.updateTask(1L, taskRequest, "test@gmail.com")
        );
    }

    @Test
    public void updateTaskWithAuthorizedUser() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        String response = taskService.updateTask(1L, taskRequest, "test@gmail.com");

        assertTrue(response.contains("Task updated successfully"));
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void deleteTaskWithNotExistingTask(){
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.deleteTask(1L, "test@gmail.com");
        });
    }

    @Test
    public void deleteTaskWithUnauthorizedUser() {
        User otherUser = new User();
        otherUser.setEmail("other@gmail.com");
        task.setUser(otherUser);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Throwable throwable = assertThrows(UnauthorizedException.class, () -> {
            taskService.deleteTask(1L, "test@gmail.com");
        });
        assertEquals("User does not have permission to delete this task", throwable.getMessage());
    }

    @Test
    public void deleteTaskWithAuthorizedUser() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        String response = taskService.deleteTask(1L, "test@gmail.com");

        verify(taskRepository).delete(task);
        assertTrue(response.contains("Task deleted successfully"));
    }

    @Test
    public void modifyTaskStatusWithNotExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.modifyTaskStatus(1L, taskStatusUpdate, "test@gmail.com");
        });
    }

    @Test
    public void modifyTaskStatusWithUnauthorizedUser() {
        User otherUser = new User();
        otherUser.setEmail("other@gmail.com");
        task.setUser(otherUser);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Throwable throwable = assertThrows(UnauthorizedException.class, () -> {
            taskService.modifyTaskStatus(1L, taskStatusUpdate, "test@gmail.com");
        });
    }

    @Test
    public void modifyTaskWithAuthorizedUser() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        String response = taskService.modifyTaskStatus(1L, taskStatusUpdate, "test@gmail.com");

        assertTrue(response.contains("Task status updated successfully"));
        verify(taskRepository).save(any(Task.class));
    }
}
