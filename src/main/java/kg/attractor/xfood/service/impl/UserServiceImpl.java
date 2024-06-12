package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.auth.RegisterUserDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final DtoBuilder dtoBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void register(RegisterUserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("User already exists");
        User user = User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .name(dto.getName())
                .surname(dto.getSurname())
                .role(dto.getRole())
                .phoneNumber(dto.getPhoneNumber())
                .build();
        userRepository.save(user);
        //NOT THE FINAL VERSION!!!
    }

    @Override
    public UserDto getUserDto() {
        return dtoBuilder.buildUserDto(
                userRepository.findByEmail(AuthParams.getPrincipal().getUsername())
                        .orElseThrow(() -> new NotFoundException("Check list not found"))
        );
    }

    @Override
    public List<UserDto> getAllExperts() {
        return userRepository.findByRole(Role.EXPERT.toString())
                .stream()
                .map(dtoBuilder::buildUserDto)
                .toList();
    }
}