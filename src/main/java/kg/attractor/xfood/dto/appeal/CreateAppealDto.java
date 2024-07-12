package kg.attractor.xfood.dto.appeal;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class CreateAppealDto {

    private String fullName;
    private String comment;
    private String email;
    private String tgLinkMessage;
    private String linkToExternalSrc;
    private Long checkListCriteriaId;
    private Date inspectionDate;
    private MultipartFile[] files;

}
