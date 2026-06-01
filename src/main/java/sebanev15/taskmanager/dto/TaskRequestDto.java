package sebanev15.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;
import sebanev15.taskmanager.model.TaskPriority;
import sebanev15.taskmanager.model.TaskStatus;

import java.time.LocalDate;

@Getter @Setter
public class TaskRequestDto {
    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDate dueDate;
}
