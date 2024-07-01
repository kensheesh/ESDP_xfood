package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.okhttp.PizzeriaManagerShiftDto;
import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ModelBuilder {
    
    //    private final ManagerServiceImpl managerService;
//    private final PizzeriaServiceImpl pizzeriaService;
//
    Manager buildManager(PizzeriaManagerShiftDto dto) {
        return Manager.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .uuid(dto.getStaffId())
                .phoneNumber(dto.getPhNumber())
                .build();
        
    }
    
    protected WorkSchedule buildWorkSchedule(PizzeriaManagerShiftDto dto, Pizzeria p, Manager m) {
        return WorkSchedule
                .builder()
                .endTime(dto.getScheduledShiftEndAtLocal())
                .startTime(dto.getScheduledShiftStartAtLocal())
                .manager(m)
                .pizzeria(p)
                .build();
    }
    
    protected Opportunity buildNewOpportunity(OpportunityCreateDto dto, LocalDate date, User user) {
        return Opportunity.builder()
                .id(dto.getId())
                .user(user)
                .date(date)
                .startTime(LocalTime.of(dto.getStartTimeHour(), dto.getStartTimeMinute()))
                .endTime(LocalTime.of(dto.getEndTimeHour(), dto.getEndTimeMinute()))
                .build();
    }
}
