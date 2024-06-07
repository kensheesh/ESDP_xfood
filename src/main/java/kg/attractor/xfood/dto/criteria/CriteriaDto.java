package kg.attractor.xfood.dto.criteria;

import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.model.Zone;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriteriaDto {

    private Long id;
    private ZoneDto zone;
    private Section section;
    private String description;
    private Integer maxValue;
    private Integer coefficient;

}
