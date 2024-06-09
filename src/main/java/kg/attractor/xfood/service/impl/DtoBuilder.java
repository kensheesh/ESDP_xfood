package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerExpertShowDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
	
	protected ChecklistExpertShowDto buildChecklistDto(CheckList model) {
		List<CriteriaExpertShowDto> criteriaDtos = model.getCheckListsCriteria().stream()
				.map(this :: bluidCriteriaShowDto)
				.collect(toList());
		
		LocalDateTime managerWorkDate = model.getWorkSchedule().getDate();
		
		ManagerExpertShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());
		
		return ChecklistExpertShowDto.builder()
				.id(model.getId())
				.status(model.getStatus())
				.criteria(criteriaDtos)
				.managerWorkDate(managerWorkDate)
				.manager(managerDto)
				.build();
	}
	
	protected CriteriaExpertShowDto bluidCriteriaShowDto(CheckListsCriteria model) {
		return CriteriaExpertShowDto.builder()
				.id(model.getCriteria().getId())
				.zone(model.getCriteria().getZone().getName())
				.section(model.getCriteria().getSection().getName())
				.description(model.getCriteria().getDescription())
				.maxValue(model.getCriteria().getMaxValue())
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
}
