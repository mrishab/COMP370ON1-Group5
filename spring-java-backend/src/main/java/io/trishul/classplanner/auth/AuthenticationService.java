package io.trishul.classplanner.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import io.trishul.classplanner.user.dto.LoginRequest;
import io.trishul.classplanner.user.dto.RegisterRequest;
import io.trishul.classplanner.user.model.User;
import io.trishul.classplanner.user.repository.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    
    public User register(RegisterRequest request) {
        User newUser = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(request.getPassword())
            .build();
        return userRepository.save(newUser);
    }
    
    public User login(LoginRequest request) {
        User probe = new User();
        probe.setEmail(request.getEmail());
        probe.setPassword(request.getPassword());
        return userRepository.findOne(Example.of(probe))
            .orElse(null);
    }

    public User authenticate(String email, String password) {
        User probe = new User();
        probe.setEmail(email);
        probe.setPassword(password);
        return userRepository.findOne(Example.of(probe))
            .orElse(null);
    }
}
