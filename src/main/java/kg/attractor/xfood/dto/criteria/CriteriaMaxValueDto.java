package kg.attractor.xfood.dto.criteria;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaMaxValueDto {
    @Positive(message = "оценка может быть только положительной")
    @Min(value = 1, message = "минимальная оценка 1 балл")
    @NotNull(message = "оценка не может быть null")
    private Integer maxValue;
    private Long criteriaId;
}
