package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DonationDto {
    private String donation_id;
    private String date;
    private String amount;
    private String family_member_id;
}
