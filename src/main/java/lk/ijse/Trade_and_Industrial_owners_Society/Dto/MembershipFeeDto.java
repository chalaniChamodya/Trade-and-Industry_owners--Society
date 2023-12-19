package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MembershipFeeDto {
    private String member_fee_id;
    private  String member_id;
    private String member_name;
    private String date;
    private String amount;
}
