package lk.ijse.Trade_and_Industrial_owners_Society.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberTm {
    private String member_id;
    private String name;
    private String personal_contact_num;
    private String business_type;
    private String nic;
}
