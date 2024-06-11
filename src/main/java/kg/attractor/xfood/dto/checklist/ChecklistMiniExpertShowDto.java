package kg.attractor.xfood.dto.checklist;

import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistMiniExpertShowDto {
	private Long id;
	private Status status;
	private ManagerShowDto manager;
	private LocalDate managerWorkDate;
	private String pizzeria;
	private String uuid;
}
