package io.trishul.classplanner.model;

public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}