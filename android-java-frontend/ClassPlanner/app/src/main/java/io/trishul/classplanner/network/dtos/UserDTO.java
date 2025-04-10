package io.trishul.classplanner.network.dtos;

import java.time.LocalDateTime;

import lombok.Data;

public class UserDTO {
    @Data
    public static class Get {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
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
