package kg.attractor.xfood.dto.appeal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppealDto {

    @NotNull(message = "Фамилия и имя не могут быть null")
    @NotEmpty(message = "Фамилия и имя не могут быть пусты")
    private String fullName;

    @NotNull(message = "Комментарий не может быть null")
    @NotEmpty(message = "Комментарий не может быть пустым")
    private String comment;

    @NotNull(message = "Почта не может быть null")
    @NotEmpty(message = "Почта не может быть пустой")
    @Email(message = "Неправильный формат почты")
    private String email;

    @NotNull(message = "Ссылка на телеграм сообщение не может быть null")
    @NotEmpty(message = "Ссылка на телеграм сообщение не может быть пустым")
    private String tgLinkMessage;
    private String linkToExternalSrc;
    private Long checkListCriteriaId;
    private Date inspectionDate;
    private MultipartFile[] files;

}
