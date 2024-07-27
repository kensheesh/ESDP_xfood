package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.user.ExpertRewardDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RewardService {

    ExpertRewardDto getExpertReward(String expertEmail, String pizzeriaName, LocalDate startDate, LocalDate endDate);

    List<ExpertRewardDto> getRewards(String pizzeria, String expert, LocalDate startDate, LocalDate endDate);
}
