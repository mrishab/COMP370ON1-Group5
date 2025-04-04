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

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}