package kg.attractor.xfood.service;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.*;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;

public interface AppealService {
    AppealSupervisorReviewDto getAppealById(Long id);

    Long create(DataAppealDto data);

    AppealDto findById(Long id);

    void update(CreateAppealDto createAppealDto, Long id);

    Integer getAmountOfNewAppeals();

    Page<AppealListDto> getAllByStatus(Boolean isAccepted, int page);

    void approve(AppealSupervisorApproveDto appeal) throws MessagingException, UnsupportedEncodingException;
}