package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.appeal.AppealSupervisorReviewDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import org.springframework.stereotype.Service;

@Service
public interface AppealService {
    void create(CreateAppealDto createDto);

    AppealSupervisorReviewDto getAppealById(Long id);
}
