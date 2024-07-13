package kg.attractor.xfood.dto.appeal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class CreateAppealDto {

    @NotNull
    private String fullName;
    @NotNull
    private String comment;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String tgLinkMessage;
    private String linkToExternalSrc;
    private Long checkListCriteriaId;
    private Date inspectionDate;
    private MultipartFile[] files;

}
