package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.LocationDto;
import kg.attractor.xfood.dto.WorkScheduleDto;
import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
    private final ChecklistCriteriaRepository checkListsCriteriaRepository;


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

    protected ManagerDto buildManagerDto(Manager manager) {
        return ManagerDto.builder()
                .id(manager.getId())
                .name(manager.getName())
                .surname(manager.getSurname())
                .phoneNumber(manager.getPhoneNumber())
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

    protected CheckListAnalyticsDto buildCheckListAnalyticsDto(CheckList model) {
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();
        checkListAnalyticsDto.setId(model.getId());
        checkListAnalyticsDto.setPizzeria(model.getWorkSchedule().getPizzeria());
        checkListAnalyticsDto.setManager(model.getWorkSchedule().getManager());
        checkListAnalyticsDto.setExpert(model.getOpportunity().getUser());
        checkListAnalyticsDto.setDate(model.getWorkSchedule().getDate().toLocalDate());

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
                .build();
    }

    protected LocationDto buildLocationDto(Location model) {
        return LocationDto.builder()
                .id(model.getId())
                .name(model.getName())
                .timezone(model.getTimezone())
                .build();
    }

    protected WorkScheduleDto buildWorkScheduleDto(WorkSchedule model) {
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
