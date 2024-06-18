package kg.attractor.xfood.dto.checklist;

import kg.attractor.xfood.dto.WorkScheduleDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListResultDto {
    private Long id;
    private String uuidLink;
    private String feedback;
    private List<CriteriaExpertShowDto> criteria;
    private WorkScheduleDto workSchedule;
}
