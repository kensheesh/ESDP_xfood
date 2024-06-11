package kg.attractor.xfood.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaveCriteriaDto {

    private Long criteriaId;
    private Integer value;
    private Long checkListId;
    private Integer maxValue;

}
