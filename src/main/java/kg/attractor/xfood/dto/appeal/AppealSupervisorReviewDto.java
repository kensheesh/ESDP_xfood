package kg.attractor.xfood.dto.appeal;

import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaSupervisorReviewDto;
import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppealSupervisorReviewDto {
    private String checkListUuid;
    private String remark;
    private Long id;
    private String email;
    private String fullName;
    private String comment;
    private String tgLinkMessage;
    private String linkToExternalSrc;
    private String respond;
    private Boolean status;
    private LocalDateTime localDate;
    private List<CommentDto> comments;
    private CheckListCriteriaSupervisorReviewDto checkListsCriteria;
    private Set<File> files = new LinkedHashSet<>();
}
