package kg.attractor.xfood.dto.pizzeria;

import kg.attractor.xfood.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzeriaDto {
    private Long id;
    private String name;
    private LocationDto location;
    private String uuid;
}
