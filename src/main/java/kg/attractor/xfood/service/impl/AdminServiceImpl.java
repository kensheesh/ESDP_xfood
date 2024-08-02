package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.user.UserEditDto;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).get();
        user.setEnabled(false);
        userRepository.save(user);
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
