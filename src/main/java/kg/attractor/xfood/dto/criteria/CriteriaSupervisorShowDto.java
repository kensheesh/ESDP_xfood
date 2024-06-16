package kg.attractor.xfood.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaSupervisorShowDto {
    private Long id;
    private String description;
    private Integer maxValue;
    private Integer coefficient;
    private String zone;
    private String section;
}
