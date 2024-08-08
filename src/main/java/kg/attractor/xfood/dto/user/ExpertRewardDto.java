package kg.attractor.xfood.dto.user;

import kg.attractor.xfood.dto.checklist.CheckListRewardDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExpertRewardDto {

    private Long countChecks;
    private Double sumRewards;
    List<CheckListRewardDto> checkListRewards;

}
