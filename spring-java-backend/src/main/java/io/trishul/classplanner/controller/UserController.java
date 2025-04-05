package io.trishul.classplanner.controller;

import io.trishul.classplanner.user.User;
import io.trishul.classplanner.service.InMemoryStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users") 
public class UserController {

    @Autowired
    private InMemoryStorageService storage;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User newUser) {
        User created = storage.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        User user = storage.getUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = storage.getUser(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updated) {
        return ResponseEntity.ok(storage.updateUser(id, updated));
    }
}