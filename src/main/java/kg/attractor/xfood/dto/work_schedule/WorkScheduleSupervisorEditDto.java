package kg.attractor.xfood.dto.work_schedule;

import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleSupervisorEditDto {
    private Long id;
    private ManagerShowDto manager;
    private PizzeriaDto pizzeria;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
