package io.trishul.classplanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradPlan {
    private Long id;
    private String userId;
    private String fileName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}