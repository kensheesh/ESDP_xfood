package kg.attractor.xfood.dto.settings;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateCreateDto {
    @NotNull
    @NotBlank

    private String templateName;
    @NotNull
    @Positive
    @Min(value = 1)
    private Double templatePrice;
    private List<CriteriaMaxValueDto> criteriaMaxValueDtoList;
}
