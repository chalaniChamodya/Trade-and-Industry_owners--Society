package lk.ijse.Trade_and_Industrial_owners_Society.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MeetingAttendanceTm {
    private String meeting_id;
    private String member_id;
    private String name;
}
