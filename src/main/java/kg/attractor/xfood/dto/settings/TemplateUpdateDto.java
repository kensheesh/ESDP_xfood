package kg.attractor.xfood.dto.settings;

import jakarta.validation.constraints.*;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.validator.UniqueTemplateName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateUpdateDto {
    @NotNull
    @NotBlank
    private String templateName;
    @NotNull
    @Positive
    @Min(value = 1)
    private Double templatePrice;
    @NotEmpty(message = "Кол-во критериев должно быть >= 1!")
    private List<CriteriaMaxValueDto> criteriaMaxValueDtoList;
}
