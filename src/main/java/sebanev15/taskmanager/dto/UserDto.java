package sebanev15.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;
import sebanev15.taskmanager.model.Task;

import java.util.List;

@Getter @Setter
public class UserDto {
    private String name;
    private String email;
    private List<Task> tasks;
}
