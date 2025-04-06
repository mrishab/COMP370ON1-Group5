package io.trishul.classplanner.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.trishul.classplanner.auth.AuthenticationService;
import io.trishul.classplanner.auth.SessionManager;
import io.trishul.classplanner.user.dto.LoginRequest;
import io.trishul.classplanner.user.dto.RegisterRequest;
import io.trishul.classplanner.user.model.User;
import io.trishul.classplanner.user.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private SessionManager sessionManager;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User created = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = authService.login(request);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updated) {
        return userRepository.findById(id)
            .map(existing -> {
                updated.setId(id);
                return ResponseEntity.ok(userRepository.save(updated));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deleteUsers(@RequestBody List<Long> ids) {
        List<User> toDelete = userRepository.findAllById(ids);
        toDelete.forEach(user -> user.setArchived(true));
        userRepository.saveAll(toDelete);
        return ResponseEntity.noContent().build();
    }
}