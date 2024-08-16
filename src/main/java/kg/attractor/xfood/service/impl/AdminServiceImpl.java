package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.dto.user.UserEditDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.AdminService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void deleteUser(Long id) {
        UserDto userDto = userService.getUserDto();
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User is not found by ID: " + id));

        boolean isValidRole = !(Objects.equals(userDto.getId(), user.getId()) ||
                !(userDto.getRole().equals(Role.SUPERVISOR) &&
                        (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.SUPERVISOR)) ||
                        (userDto.getRole().equals(Role.ADMIN) &&
                                user.getRole().equals(Role.ADMIN))));

        if (isValidRole) {
            throw new IllegalArgumentException("У вас недостаточно прав для выполнение данной функции! Ваша роль: " + userDto.getRole().toString());
        }

        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void editUser(UserEditDto userEditDto) {
        User user = userRepository.findById(userEditDto.getId()).orElseThrow(() -> new UsernameNotFoundException("User is not found by ID: " + id));
        UserDto userDto = userService.getUserDto();

        boolean isValidRole = (userDto.getRole().equals(Role.SUPERVISOR) &&
                (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.SUPERVISOR))) ||
                (userDto.getRole().equals(Role.ADMIN) &&
                        user.getRole().equals(Role.ADMIN));

        if (isValidRole) {
            throw new IllegalArgumentException("У вас недостаточно прав для выполнение данной функции! Ваша роль: " + userDto.getRole().toString());
        }
        user.setRole(userEditDto.getRole());
        user.setName(userEditDto.getName());
        user.setSurname(userEditDto.getSurname());
        user.setEnabled(userEditDto.getEnabled());
        userRepository.save(user);

    }
}
