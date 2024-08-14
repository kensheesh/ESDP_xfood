package kg.attractor.xfood.service;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.*;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AppealService {
    AppealSupervisorReviewDto getAppealById(Long id);

    Long create(DataAppealDto data);

    AppealDto findById(Long id);

    void update(CreateAppealDto createAppealDto, Long id);

    Integer getAmountOfNewAppeals();

    Page<AppealListDto> getAllByStatus(Boolean isAccepted, int page, Long pizzeriaId, Long expertId);

    void approve(AppealSupervisorApproveDto appeal) throws MessagingException, UnsupportedEncodingException;

    List<AppealDto> getAcceptedAppeals(Long checklistId, Long criteriaId);

    Long createByComment(DataAppealDto data);
}