package sebanev15.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequestDto registerRequest) {
        return userService.registerUser(registerRequest);
    }

    @Operation(summary = "Login a existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "jwt-token-of-user"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequestDto loginRequest) {
        return userService.loginUser(loginRequest);
    }

}
