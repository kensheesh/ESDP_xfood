package kg.attractor.xfood.dto.pizzeria;

import kg.attractor.xfood.model.Location;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PizzeriaDto {

    private String name;
    private Location location;
}
