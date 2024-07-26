package kg.attractor.xfood.dto.opportunity;

import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOpportunityShowDto {
    private ExpertShowDto expert;
    private List<DailyOpportunityShowDto> weeklyOpportunity;
}
