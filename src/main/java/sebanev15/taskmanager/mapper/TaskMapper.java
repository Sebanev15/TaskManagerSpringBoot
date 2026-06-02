package sebanev15.taskmanager.mapper;

import org.springframework.stereotype.Component;
import sebanev15.taskmanager.dto.TaskRequestDto;
import sebanev15.taskmanager.dto.TaskResponseDto;
import sebanev15.taskmanager.model.Task;
import sebanev15.taskmanager.model.TaskStatus;
import sebanev15.taskmanager.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {
    public Task toTask(TaskRequestDto taskRequest, User user) {
        return Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .priority(taskRequest.getPriority())
                .dueDate(taskRequest.getDueDate())
                .user(user)
                .status(taskRequest.getStatus() != null ? taskRequest.getStatus() : TaskStatus.TODO)
                .build();
    }

    public TaskResponseDto toResponseDto(Task task) {
        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setTitle(task.getTitle());
        responseDto.setDescription(task.getDescription());
        responseDto.setStatus(task.getStatus());
        responseDto.setPriority(task.getPriority());
        responseDto.setDueDate(task.getDueDate());
        responseDto.setCreatedAt(task.getCreatedAt());
        return responseDto;
    }

    public List<TaskResponseDto> toResponseDto(List<Task> tasks){
        List<TaskResponseDto> responseDtos = new ArrayList<>();
        for(Task task: tasks){
            responseDtos.add(toResponseDto(task));
        }
        return responseDtos;
    }
}
