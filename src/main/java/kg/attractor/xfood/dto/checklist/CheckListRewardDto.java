package kg.attractor.xfood.dto.checklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckListRewardDto {

    private String endDate;
    private String checklistUUID;
    private String expertName;
    private String pizzeriaName;
    private Double sumRewards;

}
