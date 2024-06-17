package kg.attractor.xfood.dto.criteria;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.model.Zone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaSupervisorCreateDto {
    @NotNull(message = "Выберите зону")
    private String zone;
    @NotNull(message = "Выберите раздел")
    private String section;
    @NotNull(message = "Введите описание")
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;

    private Integer coefficient;
}
