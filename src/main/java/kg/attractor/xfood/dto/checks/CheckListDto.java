package kg.attractor.xfood.dto.checks;

import kg.attractor.xfood.dto.work_schedule.WorkScheduleDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.WorkSchedule;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckListDto {

    private WorkScheduleDto workSchedule;
    private List<CheckListCriteriaDto> checkListsCriteriaList;
}
