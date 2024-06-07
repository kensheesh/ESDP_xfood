package kg.attractor.xfood.dto.work_schedule;

import kg.attractor.xfood.dto.pizzeria.ManagerDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalTime;

@Data
@Builder
public class WorkScheduleDto {

    private Long id;
    private ManagerDto manager;
    private PizzeriaDto pizzeria;
    private Instant date;
    private LocalTime startTime;
    private LocalTime endTime;
}
