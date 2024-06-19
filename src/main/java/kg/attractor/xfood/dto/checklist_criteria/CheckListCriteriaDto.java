package kg.attractor.xfood.dto.checklist_criteria;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckListCriteriaDto {

    private Long id;
    private String criteria;
    private Integer value;
    private Integer maxValue;
}
