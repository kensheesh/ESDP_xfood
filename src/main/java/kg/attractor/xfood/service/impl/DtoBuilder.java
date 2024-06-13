package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.LocationDto;
import kg.attractor.xfood.dto.PizzeriaDto;
import kg.attractor.xfood.dto.WorkScheduleDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.location.LocationShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DtoBuilder {

	protected ChecklistShowDto buildChecklistShowDto(CheckList model) {
		List<CriteriaExpertShowDto> criteriaDtos = model.getCheckListsCriteria().stream()
				.map(this::buildCriteriaShowDto)
				.collect(toList());

		LocalDateTime managerWorkDate = model.getWorkSchedule().getDate();
		ManagerShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());
		PizzeriaDto pizzeriaDto = buildPizzeriaDto(model.getWorkSchedule().getPizzeria());

		return ChecklistShowDto.builder()
				.id(model.getId())
				.pizzeria(pizzeriaDto)
				.manager(managerDto)
				.status(model.getStatus())
				.managerWorkDate(managerWorkDate.toString())
				.criteria(criteriaDtos)
				.build();
	}

	protected ChecklistMiniExpertShowDto buildChecklistDto(CheckList model) {
		String uuid = model.getUuidLink();
		LocalDate managerWorkDate = model.getWorkSchedule().getDate().toLocalDate();
		String pizzeria = model.getWorkSchedule().getPizzeria().getName();

		return ChecklistMiniExpertShowDto.builder()
				.id(model.getId())
				.status(model.getStatus())
				.managerWorkDate(managerWorkDate)
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


	protected CheckListResultDto buildCheckListResultDto(CheckList model) {
		return CheckListResultDto.builder()
				.id(model.getId())
				.criteria(
						model.getCheckListsCriteria().stream()
								.map(this::buildCriteriaShowDto)
								.toList()
				)
				.workSchedule(this.buildWorkScheduleDto(model.getWorkSchedule()))
				.build();
	}

	protected PizzeriaDto buildPizzeriaDto (Pizzeria model) {
		return PizzeriaDto.builder()
				.id(model.getId())
				.name(model.getName())
				.location(this.buildLocationDto(model.getLocation()))
				.build();
	}

	protected LocationDto buildLocationDto (Location model) {
		return LocationDto.builder()
				.id(model.getId())
				.name(model.getName())
				.timezone(model.getTimezone())
				.build();
	}

	protected WorkScheduleDto buildWorkScheduleDto (WorkSchedule model) {
		return WorkScheduleDto.builder()
				.id(model.getId())
				.manager(this.buildManagerShowDto(model.getManager()))
				.pizzeria(this.buildPizzeriaDto(model.getPizzeria()))
				.date(model.getDate())
				.startTime(model.getStartTime())
				.endTime(model.getEndTime())
				.build();
	}

	protected ExpertShowDto buildExpertShowDto(User user) {
		return ExpertShowDto.builder()
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
