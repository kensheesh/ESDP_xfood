package kg.attractor.xfood.dto.checklist_criteria;

import kg.attractor.xfood.dto.WorkScheduleSupervisorShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckListCriteriaSupervisorReviewDto {
    private CriteriaSupervisorShowDto criteria;
    private String pizzeria;
    private LocalDate localDate;
}
