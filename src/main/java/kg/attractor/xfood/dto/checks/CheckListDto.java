package kg.attractor.xfood.dto.checks;

import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.WorkSchedule;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CheckListDto {

    private WorkSchedule workSchedule;
    private List<CheckListsCriteria> checkListsCriteriaList;
}
