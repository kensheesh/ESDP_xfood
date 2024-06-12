package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.*;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistExpertShowDto;
import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.dto.manager.ManagerExpertShowDto;
import kg.attractor.xfood.model.*;
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

	protected CriteriaSupervisorShowDto buildCriteriaShowDto(Criteria model) {
		return CriteriaSupervisorShowDto.builder()
				.id(model.getId())
				.description(model.getDescription())
				.zone(model.getZone().getName())
				.section(model.getSection().getName())
				.coefficient(model.getCoefficient())
				.build();
	}

	protected CheckTypeSupervisorViewDto buildCheckTypeShowDto(CheckType model) {
		return CheckTypeSupervisorViewDto.builder()
				.id(model.getId())
				.name(model.getName())
				.build();
	}


	protected ManagerExpertShowDto buildManagerShowDto(Manager manager) {
		return ManagerExpertShowDto.builder()
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

	protected ZoneSupervisorShowDto buildZoneDto(Zone model){
		return ZoneSupervisorShowDto.builder()
				.id(model.getId())
				.name(model.getName())
				.build();
	}

	protected SectionSupervisorShowDto buildSectionDto(Section model){
		return SectionSupervisorShowDto.builder()
				.id(model.getId())
				.name(model.getName())
				.build();
	}
}
