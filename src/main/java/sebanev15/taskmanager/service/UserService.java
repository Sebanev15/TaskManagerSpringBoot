package sebanev15.taskmanager.service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sebanev15.taskmanager.dto.LoginRequestDto;
import sebanev15.taskmanager.dto.RegisterRequestDto;
import sebanev15.taskmanager.exception.DuplicateResourceException;
import sebanev15.taskmanager.exception.InvalidCredentialsException;
import sebanev15.taskmanager.exception.ResourceNotFoundException;
import sebanev15.taskmanager.mapper.UserMapper;
import sebanev15.taskmanager.model.User;
import sebanev15.taskmanager.repository.UserRepository;
import sebanev15.taskmanager.security.JwtService;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public String registerUser(RegisterRequestDto registerRequest){
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new DuplicateResourceException("Email already in use");
        }
        User user = userMapper.toUser(registerRequest);
        //TODO hashear el password con bcrypt
        userRepository.save(user);
        return "User registered successfully: " + registerRequest.getName();
    }

    public String loginUser(LoginRequestDto loginRequest) {
        if (userRepository.existsByEmail(loginRequest.getEmail())) {
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                return jwtService.generateToken(user);
            } else {;
                throw new InvalidCredentialsException("Invalid credentials");
            }
        }
        throw new ResourceNotFoundException("User not found");
    }
}
