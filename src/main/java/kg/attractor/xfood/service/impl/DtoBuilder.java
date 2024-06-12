package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.location.LocationShowDto;
import kg.attractor.xfood.dto.manager.ManagerExpertShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
	
	protected ChecklistExpertShowDto buildChecklistDto(CheckList model) {
		List<CriteriaExpertShowDto> criteriaDtos = model.getCheckListsCriteria().stream()
				.map(this :: buildCriteriaShowDto)
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
				.id(manager.getId())
				.name(manager.getName())
				.surname(manager.getSurname())
				.build();
	}

	protected ManagerExpertShowDto buildExpertShowDto(User user) {
		return ManagerExpertShowDto.builder()
				.id(user.getId())
				.name(user.getName())
				.surname(user.getSurname())
				.build();
	}

	protected List<LocationShowDto> buildLocationShowDtos(List<Location> locations) {
		List<LocationShowDto> dtos = new ArrayList<>();
		locations.forEach(e -> {
			dtos.add(buildLocationShowDto(e));
		});
		return dtos;
	}

	protected LocationShowDto buildLocationShowDto(Location location) {
		return LocationShowDto.builder()
				.id(location.getId())
				.name(location.getName())
				.timezone(location.getTimezone())
				.pizzerias(location.getPizzerias())
				.build();
	}

	protected List<PizzeriaShowDto> buildPizzeriaShowDtos(List<Pizzeria> pizzerias) {
		List<PizzeriaShowDto> dtos = new ArrayList<>();
		pizzerias.forEach(e -> {
			dtos.add(buildPizzeriaShowDto(e));
		});
		return dtos;
	}

	protected PizzeriaShowDto buildPizzeriaShowDto(Pizzeria pizzeria) {
		return PizzeriaShowDto.builder()
				.id(pizzeria.getId())
				.name(pizzeria.getName())
				.location(pizzeria.getLocation())
				.criteriaPizzerias(pizzeria.getCriteriaPizzerias())
				.workSchedules(pizzeria.getWorkSchedules()).build();
	}
}
