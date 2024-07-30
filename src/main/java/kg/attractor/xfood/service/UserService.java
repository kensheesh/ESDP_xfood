package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.user.ExpertRewardDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDto getUserDto();

    List<UserDto> getAllExperts();

    List<ExpertShowDto> fetchAllExperts();

    User findById(Long expertId);
}
