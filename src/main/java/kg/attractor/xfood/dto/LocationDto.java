package kg.attractor.xfood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long id;
    @NotBlank
    private String name;
    private Integer timezone;
    @NotBlank
    private CountryDto country;
}
