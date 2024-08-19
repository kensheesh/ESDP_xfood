package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.auth.RegisterUserDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.stream.Collectors;

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
    private PasswordEncoder passwordEncoder;

    @Mock
    private DtoBuilder dtoBuilder;

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

    private User createUser(Long id, String name, String surname, String email) {
        return User.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .tgLink("999")
                .email(email)
                .password("superSecretPassword123!")
                .enabled(true)
                .role(Role.EXPERT)
                .build();
    }

    private UserDto createUserDto(Long id, String name, String surname, String email, Role role) {
        return UserDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .tgLink("999")
                .email(email)
                .password("superSecretPassword123!")
                .enabled(true)
                .role(role)
                .build();
    }

    @Test
    void testRegisterUserAlreadyExists() {
        RegisterUserDto dto = new RegisterUserDto("Alex", "Johnson", "alex@gmail.com", "password", "999", "EXPERT");
        when(userRepository.getByEmail(dto.getEmail())).thenReturn(Optional.of(createUser(1L, "Alex", "Johnson", dto.getEmail())));

        assertThrows(IllegalArgumentException.class, () -> userService.register(dto));
    }

    @Test
    void testRegisterInvalidRole() {
        RegisterUserDto dto = new RegisterUserDto("Alex", "Johnson", "alex@gmail.com", "password", "999", "invalidRole");
        when(userRepository.getByEmail(dto.getEmail())).thenReturn(Optional.empty());
        userService.register(dto);
        Assertions.assertNotNull(dto);
    }

    @Test
    void testRegisterSuccess() {
        RegisterUserDto dto = new RegisterUserDto("Alex", "Johnson", "EXPERT", "password", "999", "t.me/alex");
        when(userRepository.getByEmail(dto.getEmail())).thenReturn(Optional.empty());

        userService.register(dto);

        verify(userRepository).saveUser(
                dto.getName(),
                dto.getSurname(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getTgLink(),
                Role.valueOf(dto.getRole().toUpperCase()).toString().toUpperCase()
        );
    }

    @Test
    void testFetchAllExperts() {
        List<UserDto> userDtoList = Arrays.asList(
                createUserDto(1L, "Alex", "Johnson", "john@gmail.com", Role.EXPERT),
                createUserDto(2L, "Flay", "Black", "flay@gmail.com", Role.EXPERT)
        );
        List<ExpertShowDto> expertShowDtoList = userDtoList.stream()
                .map(dtoBuilder::buildExpertShowDto)
                .collect(Collectors.toList());

        when(userRepository.findByRole(Role.EXPERT.toString())).thenReturn(userDtoList.stream()
                .map(dto -> createUser(dto.getId(), dto.getName(), dto.getSurname(), dto.getEmail()))
                .collect(Collectors.toList()));
        when(dtoBuilder.buildUserDto(any(User.class))).thenReturn(userDtoList.get(0)).thenReturn(userDtoList.get(1));
        when(dtoBuilder.buildExpertShowDto(any(UserDto.class))).thenReturn(expertShowDtoList.get(0)).thenReturn(expertShowDtoList.get(1));

        List<ExpertShowDto> result = userService.fetchAllExperts();

        assertNotNull(result);
        assertEquals(expertShowDtoList.size(), result.size());
        assertEquals(expertShowDtoList, result);
    }


    @Test
    void testIsUserExist() {
        String email = "alex@gmail.com";
        when(userRepository.getByEmail(email)).thenReturn(Optional.of(createUser(1L, "Alex", "Johnson", email)));

        assertTrue(userService.isUserExist(email));

        when(userRepository.getByEmail(email)).thenReturn(Optional.empty());

        assertFalse(userService.isUserExist(email));
    }

    @Test
    void testFindSupervisors() {
        List<User> supervisors = Collections.singletonList(createUser(1L, "Alex", "Johnson", "alex@gmail.com"));
        when(userRepository.findByRole(Role.SUPERVISOR.toString())).thenReturn(supervisors);

        List<User> result = userService.findSupervisors();

        assertNotNull(result);
        assertEquals(supervisors.size(), result.size());
    }

    @Test
    void testGetAllExperts() {
        List<User> users = Arrays.asList(
                createUser(1L, "Alex", "Johnson", "john@gmail.com"),
                createUser(2L, "Flay", "Black", "flay@gmail.com")
        );

        List<UserDto> userDtoList = users.stream()
                .map(user -> createUserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getRole()))
                .toList();

        when(userRepository.findByRole(Role.EXPERT.toString())).thenReturn(users);
        for (int i = 0; i < users.size(); i++) {
            when(dtoBuilder.buildUserDto(users.get(i))).thenReturn(userDtoList.get(i));
        }

        List<UserDto> result = userService.getAllExpertsDtos();
        assertEquals(userDtoList.size(), result.size());
        assertEquals(userDtoList, result);
    }

    @Test
    void testGetAllExpertsEmptyList() {
        when(userRepository.findByRole(Role.EXPERT.toString())).thenReturn(Collections.emptyList());

        List<UserDto> result = userService.getAllExpertsDtos();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDto() {
        User user = createUser(1L, "Alex", "Johnson", "john@gmail.com");
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
        assertThrows(NotFoundException.class, () -> userService.getUserDto());
    }
}