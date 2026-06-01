package sebanev15.taskmanager.mapper;

import org.springframework.stereotype.Component;
import sebanev15.taskmanager.dto.TaskRequestDto;
import sebanev15.taskmanager.model.Task;
import sebanev15.taskmanager.model.TaskStatus;
import sebanev15.taskmanager.model.User;

@Component
public class TaskMapper {
    public Task toTask(TaskRequestDto taskRequest, User user) {
        return Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .priority(taskRequest.getPriority())
                .dueDate(taskRequest.getDueDate())
                .user(user)
                .status(TaskStatus.TODO)
                .build();
    }
}
