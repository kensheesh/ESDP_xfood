package kg.attractor.xfood.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftTimeShowDto {
    private Long id;
    private Long opportunityId;
    private String startTime;
    private String endTime;
}
