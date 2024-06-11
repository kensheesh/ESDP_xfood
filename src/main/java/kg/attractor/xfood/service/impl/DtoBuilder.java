package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerExpertShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Pizzeria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
	
	protected ChecklistShowDto buildChecklistDto(CheckList model) {
		List<CriteriaExpertShowDto> criteriaDtos = model.getCheckListsCriteria().stream()
				.map(this :: buildCriteriaShowDto)
				.collect(toList());
		
		LocalDateTime managerWorkDate = model.getWorkSchedule().getDate();
		
		ManagerExpertShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());
		PizzeriaDto pizzeriaDto = buildPizzeriaDto(model.getWorkSchedule().getPizzeria());
		
		return ChecklistShowDto.builder()
				.pizzeria(pizzeriaDto)
				.id(model.getId())
				.status(model.getStatus())
				.criteria(criteriaDtos)
				.managerWorkDate(managerWorkDate.toString())
				.manager(managerDto)
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
	
	protected ManagerExpertShowDto buildManagerShowDto(Manager manager) {
		return ManagerExpertShowDto.builder()
				.name(manager.getName())
				.surname(manager.getSurname())
				.build();
	}

	protected PizzeriaDto buildPizzeriaDto(Pizzeria pizzeria) {
		return PizzeriaDto.builder()
				.name(pizzeria.getName())
				.build();
	}
}
