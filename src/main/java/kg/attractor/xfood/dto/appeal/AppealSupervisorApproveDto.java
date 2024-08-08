package kg.attractor.xfood.dto.appeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealSupervisorApproveDto {
    private Long appealId;
    private String respond;
    private Boolean status;
}
