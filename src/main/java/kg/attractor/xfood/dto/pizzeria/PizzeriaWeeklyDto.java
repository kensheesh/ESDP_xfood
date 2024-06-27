package kg.attractor.xfood.dto.pizzeria;

import kg.attractor.xfood.model.CriteriaPizzeria;
import kg.attractor.xfood.model.Location;
import kg.attractor.xfood.model.WorkSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzeriaWeeklyDto {
    private Long id;
    private String name;
}
