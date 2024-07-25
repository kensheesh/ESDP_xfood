package kg.attractor.xfood.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpertRewardDto {

    private Long expertId;
    private Long countChecks;
    private Long sumRewards;

}
