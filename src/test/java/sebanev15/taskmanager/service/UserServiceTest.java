package sebanev15.taskmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sebanev15.taskmanager.dto.LoginRequestDto;
import sebanev15.taskmanager.dto.RegisterRequestDto;
import sebanev15.taskmanager.exception.DuplicateResourceException;
import sebanev15.taskmanager.exception.InvalidCredentialsException;
import sebanev15.taskmanager.exception.ResourceNotFoundException;
import sebanev15.taskmanager.mapper.UserMapper;
import sebanev15.taskmanager.model.User;
import sebanev15.taskmanager.repository.UserRepository;
import sebanev15.taskmanager.security.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private RegisterRequestDto registerRequestDto = new RegisterRequestDto();
    private LoginRequestDto loginRequestDto = new LoginRequestDto();

    @BeforeEach
    public void setUp(){
        registerRequestDto.setName("Test User");
        registerRequestDto.setEmail("test@gmail.com");
        registerRequestDto.setPassword("password123");

        loginRequestDto.setEmail("test@gmail.com");
        loginRequestDto.setPassword("password123");
    }
    @Test
    public void registerMailAlreadyExists(){
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        Throwable exception = assertThrows(DuplicateResourceException.class, () -> userService.registerUser(registerRequestDto));

        assertEquals("Email already in use", exception.getMessage());
    }

    @Test
    public void registerMailNotExists(){
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(userMapper.toUser(registerRequestDto)).thenReturn(new User());

        userService.registerUser(registerRequestDto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void loginUserWithValidCredentials(){
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password123");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("fake-jwt-token");

        String token = userService.loginUser(loginRequestDto);

        assertNotNull(token);
        assertEquals("fake-jwt-token", token);
    }

    @Test
    public void loginUserEmailNotFound(){
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);

        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> userService.loginUser(loginRequestDto));

        assertEquals("User not found", throwable.getMessage());
    }

    @Test
    public void loginUserWithWrongPassword(){
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("wrongpassword");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        Throwable throwable = assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(loginRequestDto));

        assertEquals("Invalid credentials", throwable.getMessage());
    }
}
