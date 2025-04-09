package io.trishul.classplanner.classdetail.dto.classentry;

import io.trishul.classplanner.classdetail.dto.classdetail.PostClassDetailDTO;
import lombok.Data;

@Data
public class PostClassEntryDTO {
    private String course;
    private String courseNumber;
    private String description;
    private PostClassDetailDTO classDetail;
}
