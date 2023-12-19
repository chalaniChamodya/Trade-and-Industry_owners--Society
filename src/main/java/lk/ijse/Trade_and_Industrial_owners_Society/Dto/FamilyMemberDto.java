package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FamilyMemberDto {
    private String family_mem_id;
    private String member_id;
    private String name;
    private String relationship;
    private String occupation;
    private String date_of_birth;
    private String isAlive;
}
