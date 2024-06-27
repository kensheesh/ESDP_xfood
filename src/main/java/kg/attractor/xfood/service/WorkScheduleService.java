package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.WorkScheduleSupervisorShowDto;
import kg.attractor.xfood.dto.workSchedule.WeeklyScheduleShowDto;
import kg.attractor.xfood.model.WorkSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkScheduleService {
    List<WeeklyScheduleShowDto> getWeeklySchedulesByPizzeriaId(long pizzeriaId);


    WorkSchedule findWorkScheduleByManagerAndDate(Long managerId, LocalDate date);

    WorkScheduleSupervisorShowDto getWorkSchedule(Long managerId, LocalDate date);
}
