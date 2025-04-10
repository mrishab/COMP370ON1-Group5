package io.trishul.classplanner.classdetail.dto.classdetail;

import java.util.List;

import io.trishul.classplanner.classschedule.controller.dto.GetClassScheduleDTO;
import lombok.Data;

@Data
public class GetClassDetailDTO {
    private Long id;
    private String section;
    private String instructor;
    private String room;
    private String method;
    private List<GetClassScheduleDTO> schedule;
}
