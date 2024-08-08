package kg.attractor.xfood.dto.checklist;

import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
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
public class ChecklistShowDto {
	private Long id;
	private Status status;
	private PizzeriaDto pizzeria;
	private ManagerShowDto manager;
	private String managerWorkDate;
	private String managerWorkStartTime;
	private String managerWorkEndTime;
	private String uuidLink;
	private String expertEmail;
	private List<CriteriaExpertShowDto> criteria;
	private Boolean isDeleted;
}
