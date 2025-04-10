package io.trishul.classplanner.network.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {
    public static final String ATTR_ID = "id";
    public static final String ATTR_FIRST_NAME = "firstName";
    public static final String ATTR_LAST_NAME = "lastName";
    public static final String ATTR_EMAIL = "email";
    public static final String ATTR_PASSWORD = "password";
    public static final String ATTR_CREATED_AT = "createdAt";
    public static final String ATTR_UPDATED_AT = "updatedAt";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Get {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String createdAt;
        private String updatedAt;
    }

    @Data
    public static class Post {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }

    @Data
    public static class Put {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }

    @Data
    public static class Login {
        private String email;
        private String password;
    }
}
