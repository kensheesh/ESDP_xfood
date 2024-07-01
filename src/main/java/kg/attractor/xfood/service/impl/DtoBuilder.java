package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.*;
import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.dto.*;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaDto;
import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.location.LocationShowDto;
import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.workSchedule.WeekDto;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
    private final ChecklistCriteriaRepository checkListsCriteriaRepository;


    protected ChecklistMiniExpertShowDto buildChecklistDto(CheckList model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String uuid = model.getUuidLink();
        String pizzeria = model.getWorkSchedule().getPizzeria().getName();
        ManagerShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());

        return ChecklistMiniExpertShowDto.builder()
                .id(model.getId())
                .status(model.getStatus())
				.managerWorkStartDate(model.getWorkSchedule().getStartTime().format(formatter))
				.managerWorkEndDate(model.getWorkSchedule().getEndTime().format(formatter))
                .manager(managerDto)
                .pizzeria(pizzeria)
                .uuid(uuid)
                .build();
    }

    protected UserDto buildUserDto(User model) {
        return UserDto.builder()
                .id(model.getId())
                .name(model.getName())
                .surname(model.getSurname())
                .phoneNumber(model.getPhoneNumber())
                .email(model.getEmail())
                .password(model.getPassword())
                .avatar(model.getAvatar())
                .enabled(model.getEnabled())
                .role(model.getRole())
                .build();
    }


    protected ManagerDto buildManagerDto(Manager manager) {
        return ManagerDto.builder()
                .id(manager.getId())
                .name(manager.getName())
                .surname(manager.getSurname())
                .phoneNumber(manager.getPhoneNumber())
                .build();
    }

	protected ChecklistShowDto buildChecklistShowDto(CheckList model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		List<CriteriaExpertShowDto> criteriaDtos = model.getCheckListsCriteria().stream()
				.map(this::buildCriteriaShowDto)
				.sorted(Comparator.comparing(CriteriaExpertShowDto::getZone))
				.sorted(Comparator.comparing(CriteriaExpertShowDto::getSection))
				.collect(toList());
		ManagerShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());
		PizzeriaDto pizzeriaDto = buildPizzeriaDto(model.getWorkSchedule().getPizzeria());

		return ChecklistShowDto.builder()
				.uuidLink(model.getUuidLink())
				.id(model.getId())
				.pizzeria(pizzeriaDto)
				.manager(managerDto)
				.status(model.getStatus())
				.managerWorkStartDate(model.getWorkSchedule().getStartTime().format(formatter))
				.managerWorkEndDate(model.getWorkSchedule().getEndTime().format(formatter))
				.criteria(criteriaDtos)
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
				.id(manager.getId())
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
                .feedback(model.getFeedback())
                .uuidLink(model.getUuidLink())
				.workSchedule(this.buildWorkScheduleDto(model.getWorkSchedule()))
				.build();
	}

    protected CheckListAnalyticsDto buildCheckListAnalyticsDto(CheckList model) {
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();
        checkListAnalyticsDto.setId(model.getId());
        checkListAnalyticsDto.setPizzeria(model.getWorkSchedule().getPizzeria());
        checkListAnalyticsDto.setManager(model.getWorkSchedule().getManager());
        checkListAnalyticsDto.setExpert(model.getOpportunity().getUser());
        checkListAnalyticsDto.setDate(model.getWorkSchedule().getStartTime().toLocalDate());

        List<CheckListsCriteria> criterias = checkListsCriteriaRepository.findAllByChecklistId(model.getId());
        int maxvalue = 0;
        int value = 0;
        for (CheckListsCriteria criteria : criterias) {
            if (criteria.getMaxValue() != null) {
                maxvalue += criteria.getMaxValue();
            }
            value += criteria.getValue();
        }
        double result = Math.round(((double) value / maxvalue) * 100);
        checkListAnalyticsDto.setResult((int) result);

        return checkListAnalyticsDto;
    }


    protected PizzeriaDto buildPizzeriaDto(Pizzeria model) {
        return PizzeriaDto.builder()
                .id(model.getId())
                .name(model.getName())
                .location(this.buildLocationDto(model.getLocation()))
		        .uuid(model.getUuid())
                .build();
    }

    protected LocationDto buildLocationDto(Location model) {
        return LocationDto.builder()
                .id(model.getId())
                .name(model.getName())
                .timezone(model.getTimezone())
		        .countryCode(model.getCountryCode())
                .build();
    }

	protected WeekDto buildWeekDto(Long weekOrder, String mondayDate, String sundayDate) {
		return WeekDto.builder()
				.weekOrder(weekOrder)
				.monday(mondayDate)
				.sunday(sundayDate)
				.build();
	}

    protected WorkScheduleDto buildWorkScheduleDto(WorkSchedule model) {
        return WorkScheduleDto.builder()
                .id(model.getId())
                .manager(this.buildManagerShowDto(model.getManager()))
                .pizzeria(this.buildPizzeriaDto(model.getPizzeria()))
				//TODO не уверена что правильно
                .date(model.getStartTime())
                .startTime(model.getStartTime().toLocalTime())
                .endTime(model.getEndTime().toLocalTime())
                .build();
    }

    protected ExpertShowDto buildExpertShowDto(User user) {
        return ExpertShowDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    protected ExpertShowDto buildExpertShowDto(UserDto user) {
        return ExpertShowDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    protected List<LocationShowDto> buildLocationShowDtos(List<Location> locations) {
        List<LocationShowDto> dtos = new ArrayList<>();
        locations.forEach(e -> dtos.add(buildLocationShowDto(e)));
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
        pizzerias.forEach(e -> dtos.add(buildPizzeriaShowDto(e)));
        return dtos;
    }

	protected List<PizzeriaWeeklyDto> buildPizzeriaWeeklyDtos(List<Pizzeria> pizzerias) {
		List<PizzeriaWeeklyDto> dtos = new ArrayList<>();
		pizzerias.forEach(e -> dtos.add(buildPizzeriaWeeklyDto(e)));
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

	protected PizzeriaWeeklyDto buildPizzeriaWeeklyDto(Pizzeria pizzeria) {
		return PizzeriaWeeklyDto.builder()
				.id(pizzeria.getId())
				.name(pizzeria.getName())
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

	protected CriteriaSupervisorShowDto buildCriteriaSupervisorShowDto(Criteria model) {
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

	protected WorkScheduleSupervisorShowDto buildWorkScheduleShowDto(WorkSchedule model) {
		return WorkScheduleSupervisorShowDto.builder()
				.pizzeriaId(model.getPizzeria().getId())
				.endTime(model.getEndTime().toLocalTime())
				.startTime(model.getStartTime().toLocalTime())
				.build();
	}

	protected OpportunityDto buildOpportunityDto (Opportunity model) {
		return OpportunityDto.builder()
				.id(model.getId())
				.date(model.getDate())
				.startTime(model.getStartTime())
				.endTime((model.getEndTime()))
				.build();
	}

	protected CheckListCriteriaDto buildCheckListCriteriaDto(CheckListsCriteria model) {
		return CheckListCriteriaDto.builder()
				.criteria(model.getCriteria().getDescription())
				.value(model.getValue())
				.id(model.getId())
				.maxValue(model.getMaxValue())
				.build();
	}

}
