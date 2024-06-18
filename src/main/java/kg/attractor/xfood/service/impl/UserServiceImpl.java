package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.auth.RegisterUserDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
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

	public User getByEmail(String name) {
		return userRepository.getByEmail(name)
				.orElseThrow(() -> new NotFoundException("User not found"));
	}
}
