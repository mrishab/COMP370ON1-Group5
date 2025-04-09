package io.trishul.classplanner.classdetail.dto.classdetail;

import java.util.Map;

import io.trishul.classplanner.classdetail.dto.timeslot.GetTimeSlotDTO;
import lombok.Data;

@Data
public class GetClassDetailDTO {
    private Long id;
    private String section;
    private String instructor;
    private String room;
    private String method;
    private Map<String, GetTimeSlotDTO> schedule;
}
