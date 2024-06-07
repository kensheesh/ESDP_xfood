package kg.attractor.xfood.dto.criteria;

import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.model.Zone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaDto {
	private Long id;
	private Zone zone;
	private Section section;
	private String description;
	private Integer maxValue;
	private Integer coefficient;
}