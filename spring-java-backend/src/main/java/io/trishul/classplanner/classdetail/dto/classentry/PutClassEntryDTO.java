package io.trishul.classplanner.classdetail.dto.classentry;

import io.trishul.classplanner.classdetail.dto.classdetail.PutClassDetailDTO;
import lombok.Data;

@Data
public class PutClassEntryDTO {
    private String course;
    private String courseNumber;
    private String description;
    private PutClassDetailDTO classDetail;
}
