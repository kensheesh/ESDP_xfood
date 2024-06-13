package kg.attractor.xfood.dto;

import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleDto {
    private Long id;
    private ManagerShowDto manager;
    private PizzeriaDto pizzeria;
    private LocalDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
}
