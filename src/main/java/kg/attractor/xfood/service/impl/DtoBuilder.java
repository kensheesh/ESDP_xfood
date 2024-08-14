package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.*;
import kg.attractor.xfood.dto.appeal.AppealDto;
import kg.attractor.xfood.dto.appeal.AppealListDto;
import kg.attractor.xfood.dto.checklist.*;
import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaDto;
import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.location.LocationShowDto;
import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
import kg.attractor.xfood.dto.shift.ShiftDto;
import kg.attractor.xfood.dto.shift.ShiftTimeShowDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.dto.workSchedule.WeekDto;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.service.CheckTypeFeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
    private static final Logger log = LoggerFactory.getLogger(DtoBuilder.class);
    private final ChecklistCriteriaRepository checkListsCriteriaRepository;
    private final AppealRepository appealRepository;
    private final CheckTypeFeeService checkTypeFeeService;


    public ChecklistMiniExpertShowDto buildChecklistDto(CheckList model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String uuid = model.getUuidLink();
        String pizzeria = model.getWorkSchedule().getPizzeria().getName();
        ManagerShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());

        return ChecklistMiniExpertShowDto.builder()
                .id(model.getId())
                .status(model.getStatus())
                .managerWorkStartTime(model.getWorkSchedule().getStartTime().format(formatter))
                .managerWorkEndTime(model.getWorkSchedule().getEndTime().format(formatter))
                .manager(managerDto)
                .pizzeria(pizzeria)
                .uuid(uuid)
                .build();
    }

    public UserDto buildUserDto(User model) {
        return UserDto.builder()
                .id(model.getId())
                .name(model.getName())
                .surname(model.getSurname())
                .tgLink(model.getTgLink())
                .email(model.getEmail())
                .password(model.getPassword())
                .enabled(model.getEnabled())
                .role(model.getRole())
                .build();
    }


    public ManagerDto buildManagerDto(Manager manager) {
        return ManagerDto.builder()
                .id(manager.getId())
                .name(manager.getName())
                .surname(manager.getSurname())
                .phoneNumber(manager.getPhoneNumber())
                .build();
    }

    public ChecklistShowDto buildChecklistShowDto(CheckList model) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<CriteriaExpertShowDto> criteriaDtos = new ArrayList<>();
        for(var checklistCriteria : model.getCheckListsCriteria()) {
            CriteriaExpertShowDto criteriaExpertShowDto = buildCriteriaShowDto(checklistCriteria);
            List<Appeal> appeals = appealRepository.findByCheckListsCriteria(checklistCriteria);
            for(var appeal : appeals) {
                if(appeal != null && appeal.getIsAccepted() == null) {
                    criteriaExpertShowDto.setIsAccepted(true);
                }
            }
            criteriaDtos.add(criteriaExpertShowDto);
        }

        List<CriteriaExpertShowDto> sortedCriteriaDtos = criteriaDtos.stream()
                .sorted(Comparator.comparing(CriteriaExpertShowDto::getZone))
                .sorted(Comparator.comparing(CriteriaExpertShowDto::getSection))
                .collect(toList());

        ManagerShowDto managerDto = buildManagerShowDto(model.getWorkSchedule().getManager());
        PizzeriaDto pizzeriaDto = buildPizzeriaDto(model.getWorkSchedule().getPizzeria());

        return ChecklistShowDto.builder()
                .uuidLink(model.getUuidLink())
                .endTime(model.getEndTime())
                .id(model.getId())
                .pizzeria(pizzeriaDto)
                .manager(managerDto)
                .status(model.getStatus())
                .isDeleted(model.getDeleted())
                .expertEmail(model.getExpert().getEmail())
                .managerWorkDate(model.getWorkSchedule().getStartTime().format(dateTimeFormatter))
                .managerWorkStartTime(model.getWorkSchedule().getStartTime().format(timeFormatter))
                .managerWorkEndTime(model.getWorkSchedule().getEndTime().format(timeFormatter))
                .criteria(sortedCriteriaDtos)
                .build();
    }
    public ChecklistShowDto buildChecklistShowDto(CheckList model, Boolean isDeleted) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<CriteriaExpertShowDto> criteriaDtos = new ArrayList<>();
        for(var checklistCriteria : model.getCheckListsCriteria()) {
            CriteriaExpertShowDto criteriaExpertShowDto = buildCriteriaShowDto(checklistCriteria);
            List<Appeal> appeals = appealRepository.findByCheckListsCriteria(checklistCriteria);
            for(var appeal : appeals) {
                if(appeal != null && appeal.getIsAccepted() == null) {
                    criteriaExpertShowDto.setIsAccepted(true);
                }
            }
            criteriaDtos.add(criteriaExpertShowDto);
        }

        List<CriteriaExpertShowDto> sortedCriteriaDtos = criteriaDtos.stream()
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
                .endTime(model.getEndTime())
                .managerWorkStartTime(model.getWorkSchedule().getStartTime().format(formatter))
                .managerWorkEndTime(model.getWorkSchedule().getEndTime().format(formatter))
                .criteria(sortedCriteriaDtos)
                .isDeleted(isDeleted)
                .build();
    }

    public CriteriaExpertShowDto buildCriteriaShowDto(CheckListsCriteria model) {
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

    public ManagerShowDto buildManagerShowDto(Manager manager) {
        return ManagerShowDto.builder()
                .id(manager.getId())
                .name(manager.getName())
                .surname(manager.getSurname())
                .uuid(manager.getUuid())
                .phoneNumber(manager.getPhoneNumber())
                .build();
    }

    public CheckListResultDto buildCheckListResultDto(CheckList model) {
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

    public CheckListAnalyticsDto buildCheckListAnalyticsDto(CheckList model) {
        CheckListAnalyticsDto checkList = new CheckListAnalyticsDto();
        checkList.setId(model.getId());
        checkList.setPizzeria(model.getWorkSchedule().getPizzeria());
        checkList.setManager(model.getWorkSchedule().getManager());
        checkList.setExpert(model.getExpert());
        checkList.setDate(model.getWorkSchedule().getStartTime().toLocalDate());
        checkList.setUuid(model.getUuidLink());

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
        checkList.setResult((int) result);

        return checkList;
    }


    public PizzeriaDto buildPizzeriaDto(Pizzeria model) {
        return PizzeriaDto.builder()
                .id(model.getId())
                .name(model.getName())
                .location(this.buildLocationDto(model.getLocation()))
                .uuid(model.getUuid())
                .build();
    }

    public LocationDto buildLocationDto(Location model) {
        return LocationDto.builder()
                .id(model.getId())
                .name(model.getName())
                .timezone(model.getTimezone())
                .country(this.buildCountryDto(model.getCountry()))
                .build();
    }
    
    protected CountryDto buildCountryDto(Country model) {
        return CountryDto.builder()
                .id(model.getId())
                .apiUrl(model.getApiUrl())
                .countryName(model.getCountryName())
                .authUrl(model.getAuthUrl())
                .build();
    }
    
    public WeekDto buildWeekDto(Long weekOrder, String mondayDate, String sundayDate) {
        return WeekDto.builder()
                .weekOrder(weekOrder)
                .monday(mondayDate)
                .sunday(sundayDate)
                .build();
    }

    public WorkScheduleDto buildWorkScheduleDto(WorkSchedule model) {
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

    public ExpertShowDto buildExpertShowDto(User user) {
        return ExpertShowDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public ExpertShowDto buildExpertShowDto(UserDto user) {
        return ExpertShowDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public ShiftTimeShowDto buildShiftTimeShowDto(Shift shift) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return ShiftTimeShowDto.builder()
                .id(shift.getId())
                .opportunityId(shift.getOpportunity().getId())
                .startTime(shift.getStartTime().format(formatter))
                .endTime(shift.getEndTime().format(formatter)).build();
    }

    public List<ShiftTimeShowDto> buildShiftTimeShowDtos(List<Shift> shifts) {
        List<ShiftTimeShowDto> dtos = new ArrayList<>();
        shifts.forEach(e -> dtos.add(buildShiftTimeShowDto(e)));
        return dtos;
    }

    public List<LocationShowDto> buildLocationShowDtos(List<Location> locations) {
        List<LocationShowDto> dtos = new ArrayList<>();
        locations.forEach(e -> dtos.add(buildLocationShowDto(e)));
        return dtos;
    }

    public LocationShowDto buildLocationShowDto(Location location) {
        return LocationShowDto.builder()
                .id(location.getId())
                .name(location.getName())
                .timezone(location.getTimezone())
                .pizzerias(location.getPizzerias())
                .build();
    }

    public List<PizzeriaShowDto> buildPizzeriaShowDtos(List<Pizzeria> pizzerias) {
        List<PizzeriaShowDto> dtos = new ArrayList<>();
        pizzerias.forEach(e -> dtos.add(buildPizzeriaShowDto(e)));
        return dtos;
    }

    public List<PizzeriaWeeklyDto> buildPizzeriaWeeklyDtos(List<Pizzeria> pizzerias) {
        List<PizzeriaWeeklyDto> dtos = new ArrayList<>();
        pizzerias.forEach(e -> dtos.add(buildPizzeriaWeeklyDto(e)));
        return dtos;
    }

    public PizzeriaShowDto buildPizzeriaShowDto(Pizzeria pizzeria) {
        return PizzeriaShowDto.builder()
                .id(pizzeria.getId())
                .name(pizzeria.getName())
                .location(pizzeria.getLocation())
//				.criteriaPizzerias(pizzeria.getCriteriaPizzerias())
                .workSchedules(pizzeria.getWorkSchedules()).build();
    }

    public PizzeriaWeeklyDto buildPizzeriaWeeklyDto(Pizzeria pizzeria) {
        return PizzeriaWeeklyDto.builder()
                .id(pizzeria.getId())
                .name(pizzeria.getName())
                .build();
    }

    public ZoneSupervisorShowDto buildZoneDto(Zone model) {
        return ZoneSupervisorShowDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public SectionSupervisorShowDto buildSectionDto(Section model) {
        return SectionSupervisorShowDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public CriteriaSupervisorShowDto buildCriteriaSupervisorShowDto(Criteria model) {
        return CriteriaSupervisorShowDto.builder()
                .id(model.getId())
                .description(model.getDescription())
                .zone(model.getZone().getName())
                .section(model.getSection().getName())
                .coefficient(model.getCoefficient())
                .build();
    }

    public CheckTypeSupervisorViewDto buildCheckTypeShowDto(CheckType model) {
        return CheckTypeSupervisorViewDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public WorkScheduleSupervisorShowDto buildWorkScheduleShowDto(WorkSchedule model) {
        return WorkScheduleSupervisorShowDto.builder()
                .pizzeriaId(model.getPizzeria().getId())
                .endTime(model.getEndTime().toLocalTime())
                .startTime(model.getStartTime().toLocalTime())
                .build();
    }

    public OpportunityDto buildOpportunityDto(Opportunity model) {
        return OpportunityDto.builder()
                .id(model.getId())
                .isDayOff(model.getIsDayOff())
                .shifts(model.getShifts().stream()
                        .map(this::buildShiftDto)
                        .toList())
                .build();
    }

    public ShiftDto buildShiftDto(Shift model) {
        return ShiftDto.builder()
                .id(model.getId())
                .startTime(model.getStartTime())
                .endTime(model.getEndTime())
                .build();
    }

    public CheckListCriteriaDto buildCheckListCriteriaDto(CheckListsCriteria model) {
        return CheckListCriteriaDto.builder()
                .criteria(model.getCriteria().getDescription())
                .value(model.getValue())
                .id(model.getId())
                .maxValue(model.getMaxValue())
                .build();
    }

    public AppealDto buildAppealDto(Appeal model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                .withZone(ZoneId.systemDefault());
        AppealDto appealDto = AppealDto.builder()
                .linkToExternalSrc(model.getLinkToExternalSrc())
                .tgLinkMessage(model.getTgLinkMessage())
                .fullName(model.getFullName())
                .criteriaDescription(model.getCheckListsCriteria().getCriteria().getDescription())
                .managerName(model.getCheckListsCriteria().getChecklist().getWorkSchedule().getManager().getName())
                .managerSurname(model.getCheckListsCriteria().getChecklist().getWorkSchedule().getManager().getSurname())
                .pizzeriaName(model.getCheckListsCriteria().getChecklist().getWorkSchedule().getPizzeria().getName())
                .date(formatter.format(model.getCheckListsCriteria().getChecklist().getEndTime()))
                .comment(model.getComment_expert())
                .checklistUuid(model.getCheckListsCriteria().getChecklist().getUuidLink())
                .id(model.getId())
                .email(model.getEmail())
                .build();
         if (model.getComment() != null){
             appealDto.setRemark(model.getComment().getComment());
         }
         return appealDto;
    }

	public AppealListDto buildAppealsListDto (Appeal model) {
		return AppealListDto.builder()
				.id(model.getId())
				.fullName(model.getFullName())
				.pizzeriaName(model.getCheckListsCriteria().getChecklist().getWorkSchedule().getPizzeria().getName())
				.locationName(model.getCheckListsCriteria().getChecklist().getWorkSchedule().getPizzeria().getLocation().getName())
				.expertFullName(model.getCheckListsCriteria().getChecklist().getExpert().getName() + " "
						+ model.getCheckListsCriteria().getChecklist().getExpert().getSurname() )
				.build();
	}

    public CheckListRewardDto buildCheckListRewardDto(CheckList model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Long checkTypeId = model.getCheckType().getId();
        return CheckListRewardDto.builder()
                .checklistUUID(model.getUuidLink())
                .endDate(formatter.format(model.getEndTime()))
                .expertName(model.getExpert().getName() + " " + model.getExpert().getSurname())
                .pizzeriaName(model.getWorkSchedule().getPizzeria().getName())
                .sumRewards(checkTypeFeeService.getFeesByCheckTypeId(checkTypeId).doubleValue())
                .build();
    }
}
