package kg.attractor.xfood.dto.checklist;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CheckListRewardDto {

    private String endDate;
    private String checklistUUID;
    private String expertName;
    private String pizzeriaName;
    private Double sumRewards;

}
