package kg.attractor.xfood.dto.location;

import kg.attractor.xfood.model.Pizzeria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationShowDto {
    private Long id;
    private String name;
    private Integer timezone;
    private List<Pizzeria> pizzerias;
}
