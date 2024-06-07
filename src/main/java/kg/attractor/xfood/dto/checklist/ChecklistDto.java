package kg.attractor.xfood.dto.checklist;

import kg.attractor.xfood.dto.checklistCriteria.ChecklistCriteriaDto;
import kg.attractor.xfood.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistDto {
	private Long id;
	private Status status;
	private List<ChecklistCriteriaDto> checklistCriteria;
//	private List<CriteriaDto> criteria;
}