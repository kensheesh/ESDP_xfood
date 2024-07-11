package kg.attractor.xfood.service;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.AppealSupervisorApproveDto;
import kg.attractor.xfood.dto.appeal.AppealSupervisorReviewDto;
import kg.attractor.xfood.dto.appeal.AppealDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.dto.appeal.DataAppealDto;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface AppealService {
    void create(CreateAppealDto createDto);

    AppealSupervisorReviewDto getAppealById(Long id);

    void approve(AppealSupervisorApproveDto appeal);
   
    Long create(DataAppealDto data);
    
    AppealDto findById(Long id);

    void update(CreateAppealDto createAppealDto, Long id);
    void approve(AppealSupervisorApproveDto appeal) throws MessagingException, UnsupportedEncodingException;
}
