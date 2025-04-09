package io.trishul.classplanner.classdetail.dto.classentry;

import io.trishul.classplanner.classdetail.dto.classdetail.GetClassDetailDTO;
import lombok.Data;

@Data
public class GetClassEntryDTO {
    private Long id;
    private String course;
    private String courseNumber;
    private String description;
    private GetClassDetailDTO classDetail;
}
