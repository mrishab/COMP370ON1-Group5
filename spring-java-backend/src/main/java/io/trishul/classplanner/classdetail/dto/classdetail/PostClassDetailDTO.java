package io.trishul.classplanner.classdetail.dto.classdetail;

import java.util.Map;

import io.trishul.classplanner.classdetail.dto.timeslot.PostTimeSlotDTO;
import lombok.Data;

@Data
public class PostClassDetailDTO {
    private String section;
    private String instructor;
    private String room;
    private String method;
    private Map<String, PostTimeSlotDTO> schedule;
}
