package kg.attractor.xfood.dto.pizzeria;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManagerDto {

    private String name;
    private String surname;
    private String phoneNumber;

}
