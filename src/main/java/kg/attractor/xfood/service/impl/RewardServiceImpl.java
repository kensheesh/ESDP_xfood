package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.CheckListRewardDto;
import kg.attractor.xfood.dto.user.ExpertRewardDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.RewardService;
import kg.attractor.xfood.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final UserRepository userRepository;
    private final CheckListService checkListService;
    private final UserServiceImpl userServiceImpl;

    @Override
    public ExpertRewardDto getExpertReward(String expertEmail, String pizzeriaName, LocalDate startDate, LocalDate endDate) {
        List<CheckListRewardDto> rewardDtoList = getCheckListReward(expertEmail, startDate, endDate, pizzeriaName);
        Double sumRewards = rewardDtoList.stream()
                .mapToDouble(CheckListRewardDto::getSumRewards)
                .sum();

        return ExpertRewardDto.builder()
                .checkListRewards(rewardDtoList)
                .sumRewards(sumRewards)
                .countChecks((long) rewardDtoList.size())
                .build();
    }

    @Override
    public List<ExpertRewardDto> getRewards(String pizzeria, String expert, LocalDate startDate, LocalDate endDate) {
        List<ExpertRewardDto> experts = new ArrayList<>();
        if(expert.equals("default")) {
            Specification<User> spec = Specification
                    .where(UserSpecification.hasRole(Role.EXPERT));
            List<User> users = userRepository.findAll(spec);
            for(var user : users) {
                ExpertRewardDto expertRewardDto = getExpertReward(user.getEmail(), pizzeria, startDate, endDate);
                experts.add(expertRewardDto);
            }
        } else {
            User user = userRepository.findById(Long.parseLong(expert)).get();
            experts.add(getExpertReward(user.getEmail(), pizzeria, startDate, endDate));
        }
        return experts;
    }

    private List<CheckListRewardDto> getCheckListReward(String expertEmail, LocalDate startDate, LocalDate endDate, String pizzeriaName) {
        LocalDateTime startDateTime = null, endDateTime = null;
        if(startDate != null && endDate != null) {
            startDateTime = startDate.atStartOfDay();
            endDateTime = endDate.atStartOfDay();
        }
        return checkListService.getChecklistRewardsByExpert(expertEmail, startDateTime, endDateTime, pizzeriaName);
    }
}
