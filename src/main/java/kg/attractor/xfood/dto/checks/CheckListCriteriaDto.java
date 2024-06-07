package kg.attractor.xfood.dto.checks;

import kg.attractor.xfood.dto.criteria.CriteriaDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckListCriteriaDto {

    private CriteriaDto criteria;

}
