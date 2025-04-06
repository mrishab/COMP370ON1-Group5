package io.trishul.classplanner.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.trishul.classplanner.auth.AuthenticationService;
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

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = authService.login(request);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public List<GetUserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
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

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GetUserDTO> updateUser(@PathVariable Long id, @RequestBody PutUserDTO dto) {
        return userRepository.findById(id)
                .map(user -> {
                    userMapper.updateEntity(user, dto);
                    return userMapper.toGetDTO(userRepository.save(user));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> ids) {
        userRepository.softDelete(ids);
        return ResponseEntity.noContent().build();
    }
}