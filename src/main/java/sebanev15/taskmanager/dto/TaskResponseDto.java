package sebanev15.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;
import sebanev15.taskmanager.model.TaskPriority;
import sebanev15.taskmanager.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
}
