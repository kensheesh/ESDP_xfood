package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserDto();

    List<UserDto> getAllExperts();
}
