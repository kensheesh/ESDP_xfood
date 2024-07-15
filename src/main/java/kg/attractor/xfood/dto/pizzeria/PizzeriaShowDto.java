package kg.attractor.xfood.dto.pizzeria;

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
public class PizzeriaShowDto {
    private Long id;
    private String name;
    private Location location;
    //    private List<CriteriaPizzeria> criteriaPizzerias;
    private List<WorkSchedule> workSchedules;
}
