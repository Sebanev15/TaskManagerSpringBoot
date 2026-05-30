package sebanev15.taskmanager.mapper;

import org.springframework.stereotype.Component;
import sebanev15.taskmanager.dto.RegisterRequestDto;
import sebanev15.taskmanager.model.User;

@Component
public class UserMapper {
    public User toUser(RegisterRequestDto registerRequestDto){
        return User.builder()
                .name(registerRequestDto.getName())
                .email(registerRequestDto.getEmail())
                .password(registerRequestDto.getPassword())
                .build();
    }
}
