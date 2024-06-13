package kg.attractor.xfood.dto.work_schedule;

import kg.attractor.xfood.dto.PizzeriaDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class WorkScheduleDto {

    private Long id;
    private ManagerShowDto manager;
    private PizzeriaDto pizzeria;
    private String date;
    private LocalTime startTime;
    private LocalTime endTime;
}
