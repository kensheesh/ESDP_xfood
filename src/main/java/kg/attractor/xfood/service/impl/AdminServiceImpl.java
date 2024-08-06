package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.dto.user.UserEditDto;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.AdminService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void deleteUser(Long id) {
        UserDto userDto = userService.getUserDto();
        User user = userRepository.findById(id).get();
        if(!user.getId().equals(userDto.getId())) {
            user.setEnabled(false);
            userRepository.save(user);
        }
    }

    @Override
    public void editUser(UserEditDto userEditDto) {
        User user = userRepository.findById(userEditDto.getId()).get();
        user.setRole(userEditDto.getRole());
        user.setName(userEditDto.getName());
        user.setSurname(userEditDto.getSurname());
        user.setEnabled(userEditDto.getEnabled());
        userRepository.save(user);
    }
}
