package kg.attractor.xfood.dto.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import kg.attractor.xfood.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzeriaDto implements Serializable {
    private Long id;
    @NotBlank
    private String name, uuid;
    @JsonProperty("locationId")
    private Long locationId;
    private LocationDto location;
}
