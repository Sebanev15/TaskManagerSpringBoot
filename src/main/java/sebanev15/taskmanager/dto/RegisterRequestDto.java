package sebanev15.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
}
