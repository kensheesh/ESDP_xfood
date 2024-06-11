package kg.attractor.xfood.dto;

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
}
