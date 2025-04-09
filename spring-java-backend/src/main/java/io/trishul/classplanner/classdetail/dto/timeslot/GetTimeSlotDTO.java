package io.trishul.classplanner.classdetail.dto.timeslot;

import lombok.Data;

@Data
public class GetTimeSlotDTO {
    private Long id;
    private String start;
    private String end;
}
