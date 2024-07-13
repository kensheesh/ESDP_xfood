package kg.attractor.xfood.dto.appeal;

import kg.attractor.xfood.model.CheckListsCriteria;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppealDto {

    private Long id;
    private String email;
    private String fullName;
    private String comment;
    private String tgLinkMessage;
    private String linkToExternalSrc;
    private CheckListsCriteria checkListsCriteria;

}
