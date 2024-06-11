package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
	
	protected ChecklistMiniExpertShowDto buildChecklistDto(CheckList model) {
		String uuid = model.getUuidLink();
		LocalDate managerWorkDate = model.getWorkSchedule().getDate().toLocalDate();
		String pizzeria = model.getWorkSchedule().getPizzeria().getName();
		ManagerShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());
		
		return ChecklistMiniExpertShowDto.builder()
				.id(model.getId())
				.status(model.getStatus())
				.managerWorkDate(managerWorkDate)
				.manager(managerDto)
				.pizzeria(pizzeria)
				.uuid(uuid)
				.build();
	}
	
	protected CriteriaExpertShowDto buildCriteriaShowDto(CheckListsCriteria model) {
		return CriteriaExpertShowDto.builder()
				.id(model.getCriteria().getId())
				.zone(model.getCriteria().getZone().getName())
				.section(model.getCriteria().getSection().getName())
				.description(model.getCriteria().getDescription())
				.maxValue(model.getMaxValue())
				.coefficient(model.getCriteria().getCoefficient())
				.value(model.getValue())
				.build();
	}
	
	protected ManagerShowDto buildManagerShowDto(Manager manager) {
		return ManagerShowDto.builder()
				.name(manager.getName())
				.surname(manager.getSurname())
				.build();
	}
}
