package kg.attractor.xfood.dto.checklist;

import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerExpertShowDto;
import kg.attractor.xfood.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistExpertShowDto {
	private Long id;
	private Status status;
	private ManagerExpertShowDto manager;
	private LocalDateTime managerWorkDate;
	private List<CriteriaExpertShowDto> criteria;
}
