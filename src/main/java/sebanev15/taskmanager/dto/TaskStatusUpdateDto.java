package sebanev15.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;
import sebanev15.taskmanager.model.TaskStatus;

@Getter @Setter
public class TaskStatusUpdateDto {
    private TaskStatus status;
}
