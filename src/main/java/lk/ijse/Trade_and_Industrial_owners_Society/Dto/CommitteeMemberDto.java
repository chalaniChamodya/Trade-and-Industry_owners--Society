package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommitteeMemberDto {
    private String com_mem_id;
    private String member_id;
    private String name;
    private String position;
    private String date;
}
