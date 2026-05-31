package sebanev15.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sebanev15.taskmanager.dto.LoginRequestDto;
import sebanev15.taskmanager.dto.RegisterRequestDto;
import sebanev15.taskmanager.mapper.UserMapper;
import sebanev15.taskmanager.model.User;
import sebanev15.taskmanager.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequestDto registerRequest) {
        return userService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequestDto loginRequest) {
        return userService.loginUser(loginRequest);
    }

}
