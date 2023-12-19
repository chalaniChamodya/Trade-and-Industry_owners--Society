package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeetingAttendanceDto {
    private String meeting_id;
    private String member_id;
    private String member_name;
    private String date;
    private String time;
}
