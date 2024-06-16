package kg.attractor.xfood.dto.checklist;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.model.Pizzeria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListMiniSupervisorCreateDto {
    private Long opportunityId;
    private Pizzeria pizzeria;
    private Long workScheduleId;
    List< CriteriaMaxValueDto> criteriaMaxValueDtoList ;
}
