package kg.attractor.xfood.dto.checklist;


import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityEditDto;
import kg.attractor.xfood.dto.work_schedule.WorkScheduleSupervisorEditDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CheckListSupervisorEditDto {
    private String id;
    private WorkScheduleSupervisorEditDto workSchedule;
    private OpportunityEditDto opportunity;
    List<CriteriaExpertShowDto> criterion;

}
