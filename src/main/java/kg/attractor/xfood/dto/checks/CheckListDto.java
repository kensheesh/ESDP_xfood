package kg.attractor.xfood.dto.checks;

import kg.attractor.xfood.dto.work_schedule.WorkScheduleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckListDto {

    private Long id;
    private WorkScheduleDto workSchedule;
    private List<CheckListCriteriaDto> checkListsCriteriaList;
}
