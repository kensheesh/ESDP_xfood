package kg.attractor.xfood.service;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.AppealSupervisorApproveDto;
import kg.attractor.xfood.dto.appeal.AppealSupervisorReviewDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface AppealService {
    void create(CreateAppealDto createDto);

    AppealSupervisorReviewDto getAppealById(Long id);

    void approve(AppealSupervisorApproveDto appeal) throws MessagingException, UnsupportedEncodingException;
}
