package sebanev15.taskmanager.service;

import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sebanev15.taskmanager.dto.LoginRequestDto;
import sebanev15.taskmanager.dto.RegisterRequestDto;
import sebanev15.taskmanager.mapper.UserMapper;
import sebanev15.taskmanager.model.User;
import sebanev15.taskmanager.repository.UserRepository;

import javax.xml.validation.Validator;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public String registerUser(RegisterRequestDto registerRequest){
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return "Email already exists";
        }else{
            User user = userMapper.toUser(registerRequest);
            //TODO hashear el password con bcrypt
            userRepository.save(user);
            return "User registered successfully: " + registerRequest.getName();
        }
    }

    public User loginUser(LoginRequestDto loginRequest) {
        if (userRepository.existsByEmail(loginRequest.getEmail())) {
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                //TODO generar JWT token
                return user;
            } else {;
                return null; // Invalid password
            }
        }
        return null;
    }
}
