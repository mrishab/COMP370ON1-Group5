package io.trishul.classplanner.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.auth.AuthenticationService;
import io.trishul.classplanner.auth.SessionManager;
import io.trishul.classplanner.user.controller.dto.GetUserDTO;
import io.trishul.classplanner.user.controller.dto.PostUserDTO;
import io.trishul.classplanner.user.controller.dto.PutUserDTO;
import io.trishul.classplanner.user.controller.dto.mapper.UserMapper;
import io.trishul.classplanner.user.dto.LoginRequest;
import io.trishul.classplanner.user.model.User;
import io.trishul.classplanner.user.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private SessionManager sessionManager;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = authService.login(request);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserDTO> getUser() {
        return userRepository.findById(sessionManager.getCurrentUserId())
                .map(userMapper::toGetDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    @Transactional
    public GetUserDTO createUser(@RequestBody PostUserDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toGetDTO(userRepository.save(user));
    }

    @PutMapping("/me")
    @Transactional
    public ResponseEntity<GetUserDTO> updateUser(@RequestBody PutUserDTO dto) {
        return userRepository.findById(sessionManager.getCurrentUserId())
                .map(user -> {
                    userMapper.updateEntity(user, dto);
                    return userMapper.toGetDTO(userRepository.save(user));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/me")
    @Transactional
    public ResponseEntity<Void> deleteUsers() {
        userRepository.softDelete(List.of(sessionManager.getCurrentUserId()), null);
        sessionManager.endSession();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/archived")
    @Transactional
    public ResponseEntity<Void> activateUsers(@RequestParam List<Long> ids) {
        userRepository.restore(ids, null);
        return ResponseEntity.noContent().build();
    }
}