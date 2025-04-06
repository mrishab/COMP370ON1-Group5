package io.trishul.classplanner.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.data.domain.Example;

import io.trishul.classplanner.user.User;
import io.trishul.classplanner.user.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User newUser) {
        User created = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        User probe = new User();
        probe.setEmail(loginRequest.getEmail());
        probe.setPassword(loginRequest.getPassword());
        probe.setArchived(false);
        
        return userRepository.findOne(Example.of(probe))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userRepository.findById(Long.parseLong(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updated) {
        return userRepository.findById(Long.parseLong(id))
            .map(existing -> {
                updated.setId(Long.parseLong(id));
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