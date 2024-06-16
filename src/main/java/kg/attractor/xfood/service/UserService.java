package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserDto();

    List<UserDto> getAllExperts();

    User findById(Long expertId);
}
