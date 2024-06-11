package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.LocationDto;
import kg.attractor.xfood.dto.PizzeriaDto;
import kg.attractor.xfood.dto.WorkScheduleDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;

import kg.attractor.xfood.model.*;
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

	protected CheckListResultDto buildCheckListResultDto(CheckList model) {
		return CheckListResultDto.builder()
				.id(model.getId())
				.criteria(
						model.getCheckListsCriteria().stream()
								.map(this :: buildCriteriaShowDto)

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
}
