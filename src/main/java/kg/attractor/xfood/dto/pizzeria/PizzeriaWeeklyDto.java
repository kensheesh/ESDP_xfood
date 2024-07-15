package kg.attractor.xfood.dto.pizzeria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzeriaWeeklyDto {
    private Long id;
    private String name;
}
