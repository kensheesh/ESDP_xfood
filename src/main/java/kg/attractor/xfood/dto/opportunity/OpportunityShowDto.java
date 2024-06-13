package kg.attractor.xfood.dto.opportunity;

import kg.attractor.xfood.dto.expert.ExpertShowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityShowDto {
    private ExpertShowDto user;
    private List<DailyOpportunityShowDto> shifts;

}
