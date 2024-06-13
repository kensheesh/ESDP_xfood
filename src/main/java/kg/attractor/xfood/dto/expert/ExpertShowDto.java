package kg.attractor.xfood.dto.expert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertShowDto {
    private Long id;
    private String name;
    private String surname;
}
