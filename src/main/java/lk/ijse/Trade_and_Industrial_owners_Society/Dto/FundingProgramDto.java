package lk.ijse.Trade_and_Industrial_owners_Society.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FundingProgramDto {
    private String program_id;
    private String program_name;
    private String description;
    private String date;
    private String location;
    private String income;
    private String expenditure;
}
