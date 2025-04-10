package io.trishul.classplanner.classdetail.dto.course;

import io.trishul.classplanner.classdetail.dto.classdetail.GetClassDetailDTO;
import lombok.Data;

@Data
public class GetCourseDTO {
    private Long id;
    private String subject;
    private String number;
    private String title;
    private GetClassDetailDTO classDetail;
}
