package io.trishul.classplanner.model;

public class User {
    public static final String ATTR_NAME_ID = "id";
    public static final String ATTR_NAME_EMAIL = "email";
    public static final String ATTR_NAME_PASSWORD = "password";
    public static final String ATTR_NAME_FIRST_NAME = "firstName";
    public static final String ATTR_NAME_LAST_NAME = "lastName";

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User() {}

    public User(Long id, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}