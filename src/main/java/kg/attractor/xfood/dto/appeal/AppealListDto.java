package kg.attractor.xfood.dto.appeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppealListDto {
    private Long id;
    private String fullName;
    private String pizzeriaName;
    private String locationName;
    private String expertFullName;
}
