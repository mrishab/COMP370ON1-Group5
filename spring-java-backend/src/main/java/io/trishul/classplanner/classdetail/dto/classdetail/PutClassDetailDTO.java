package io.trishul.classplanner.classdetail.dto.classdetail;

import java.util.Map;

import io.trishul.classplanner.classdetail.dto.timeslot.PutTimeSlotDTO;
import lombok.Data;

@Data
public class PutClassDetailDTO {
    private String section;
    private String instructor;
    private String room;
    private String method;
    private Map<String, PutTimeSlotDTO> schedule;
}
