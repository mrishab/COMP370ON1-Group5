package io.trishul.classplanner.user.controller.dto;

import lombok.Data;

@Data
public class PutUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
