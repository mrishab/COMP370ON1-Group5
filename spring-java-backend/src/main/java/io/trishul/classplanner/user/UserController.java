package io.trishul.classplanner.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
    User probe = User.builder().email(loginRequest.getEmail()).password(loginRequest.getPassword())
        .build();
    Example<User> example = Example.of(probe);

    User user = userRepository.findOne(example).orElse(null);

    if (user == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(user);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody RegisterRequest registerRequest) {
    User newUser = User.builder().firstName(registerRequest.getFirstName())
        .lastName(registerRequest.getLastName()).email(registerRequest.getEmail())
        .password(registerRequest.getPassword()).build();

    User savedUser = userRepository.save(newUser);
    return ResponseEntity.ok(savedUser);
  }

  @GetMapping("")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable("id") Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @PostMapping("")
  public User createUser(@RequestBody User user) {
    return userRepository.save(user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable("id") Long id) {
    userRepository.deleteById(id);
  }
}
