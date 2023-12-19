package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
    private String member_id;
    private String name_with_initials;
    private String full_name;
    private String business_address;
    private String personal_address;
    private String business_type;
    private String nic;
    private String email;
    private String date_of_birth;
    private String personal_contact_num;
    private String business_contact_num;
    private String admission_fee;
    private String joined_date;
}
