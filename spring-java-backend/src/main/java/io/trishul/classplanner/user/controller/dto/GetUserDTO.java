package io.trishul.classplanner.user.controller.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GetUserDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
