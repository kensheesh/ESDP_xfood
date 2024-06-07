package kg.attractor.xfood.dto.pizzeria;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.attractor.xfood.model.Location;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PizzeriaDto {

    private String name;
    private Location location;
}
