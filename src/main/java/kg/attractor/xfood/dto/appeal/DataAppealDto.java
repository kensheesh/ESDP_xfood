package kg.attractor.xfood.dto.appeal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataAppealDto {
    private Long commentId;
    private Long criteriaId;
    private Long checkListId;
}
