package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionFeeDto {
    private String subscription_fee_id;
    private  String member_id;
    private String member_name;
    private String date;
    private String amount;
}
