package kg.attractor.xfood.dto.checklistCriteria;

import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.Criteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistCriteriaDto {
	private Long id;
	private Criteria criteria;
	private CheckList checklist;
	private Integer value;
}
