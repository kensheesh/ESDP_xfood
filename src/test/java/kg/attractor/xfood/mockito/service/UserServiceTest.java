package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.auth.RegisterUserDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUpSecurityContext();
    }

    private void setUpSecurityContext() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("john@gmail.com")
                .password("superSecretPassword123!")
                .roles(String.valueOf(Role.EXPERT))
                .build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    private User createUser(Long id, String name, String surname, String email, Role role) {
        return User.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .phoneNumber("999")
                .email(email)
                .password("superSecretPassword123!")
                .avatar("defaultAvatar.png")
                .enabled(true)
                .role(role)
                .build();
    }

    private UserDto createUserDto(Long id, String name, String surname, String email, Role role) {
        return UserDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .phoneNumber("999")
                .email(email)
                .password("superSecretPassword123!")
                .avatar("defaultAvatar.png")
                .enabled(true)
                .role(role)
                .build();
    }

    @Test
    void testRegisterUser() {
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .name("Alex")
                .surname("Johnson")
                .email("john@gmail.com")
                .phoneNumber("999")
                .password("superSecretPassword123!")
                .role(Role.EXPERT)
                .build();

        String encodedPassword = "encodedPassword123";
        when(userRepository.existsByEmail(registerUserDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerUserDto.getPassword())).thenReturn(encodedPassword);

        userService.register(registerUserDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertNotNull(savedUser);
        assertEquals(registerUserDto.getName(), savedUser.getName());
        assertEquals(registerUserDto.getSurname(), savedUser.getSurname());
        assertEquals(registerUserDto.getEmail(), savedUser.getEmail());
        assertEquals(registerUserDto.getPhoneNumber(), savedUser.getPhoneNumber());
        assertEquals(Role.EXPERT, savedUser.getRole());
        assertEquals(encodedPassword, savedUser.getPassword());
    }

    @Test
    void testGetAllExperts() {
        List<User> users = Arrays.asList(
                createUser(1L, "Alex", "Johnson", "john@gmail.com", Role.EXPERT),
                createUser(2L, "Flay", "Black", "flay@gmail.com", Role.EXPERT)
        );

        List<UserDto> userDtos = users.stream()
                .map(user -> createUserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getRole()))
                .toList();

        when(userRepository.findByRole(Role.EXPERT.toString())).thenReturn(users);
        for (int i = 0; i < users.size(); i++) {
            when(dtoBuilder.buildUserDto(users.get(i))).thenReturn(userDtos.get(i));
        }

        List<UserDto> result = userService.getAllExperts();
        assertEquals(userDtos.size(), result.size());
        assertEquals(userDtos, result);
    }

    @Test
    void testGetAllExpertsEmptyList() {
        when(userRepository.findByRole(Role.EXPERT.toString())).thenReturn(Collections.emptyList());

        List<UserDto> result = userService.getAllExperts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDto() {
        User user = createUser(1L, "Alex", "Johnson", "john@gmail.com", Role.EXPERT);
        UserDto userDto = createUserDto(1L, "Alex", "Johnson", "john@gmail.com", Role.EXPERT);

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        when(dtoBuilder.buildUserDto(user)).thenReturn(userDto);

        UserDto userGet = userService.getUserDto();

        assertNotNull(userGet);
        assertEquals(userDto, userGet);
    }

    @Test
    void testGetUserDtoUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.getUserDto();
        });
    }
}