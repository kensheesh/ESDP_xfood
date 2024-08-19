package kg.attractor.xfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckTypeShowDto {
    private Long id;
    private String name;
    private Integer numsOfCriteria;
    private Integer totalValue;
    private BigDecimal fee;
}
