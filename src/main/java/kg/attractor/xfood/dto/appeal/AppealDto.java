package kg.attractor.xfood.dto.appeal;

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
    private String criteriaDescription;
    private String managerName;
    private String managerSurname;
    private String pizzeriaName;
    private String checklistUuid;
    private String date;
    private String remark;

}
