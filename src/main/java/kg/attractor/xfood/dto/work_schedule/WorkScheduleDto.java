package kg.attractor.xfood.dto.work_schedule;

import kg.attractor.xfood.dto.manager.ManagerExpertShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class WorkScheduleDto {

    private Long id;
    private ManagerExpertShowDto manager;
    private PizzeriaDto pizzeria;
    private String date;
    private LocalTime startTime;
    private LocalTime endTime;
}
