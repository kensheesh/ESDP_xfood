package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.workSchedule.WeeklyScheduleShowDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkScheduleService {
    List<WeeklyScheduleShowDto> getWeeklySchedulesByPizzeriaId(long pizzeriaId);

    Long getPizzeriaId(Long managerId, LocalDateTime date);
}
