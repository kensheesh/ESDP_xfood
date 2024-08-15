package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.dto.user.UserEditDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.UserService;
import kg.attractor.xfood.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {
    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser(Long id, Role role) {
        return User.builder()
                .id(id)
                .name("John")
                .surname("Doe")
                .enabled(true)
                .role(role)
                .build();
    }

    private UserDto createUserDto(Long id) {
        return UserDto.builder()
                .id(id)
                .build();
    }

    private UserEditDto createUserEditDto(Long id, String name, boolean enabled) {
        return UserEditDto.builder()
                .id(id)
                .name(name)
                .surname("Doe")
                .role(Role.valueOf("ADMIN"))
                .enabled(enabled)
                .build();
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;
        User user = createUser(userId, Role.EXPERT);
        UserDto currentUserDto = createUserDto(2L); // Different ID

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userService.getUserDto()).thenReturn(currentUserDto);

        adminService.deleteUser(userId);

        verify(userRepository).save(user);
        assertTrue(user.isEnabled());
    }

    @Test
    void testDeleteUser_FailCurrentUser() {
        Long userId = 1L;
        User user = createUser(userId, Role.ADMIN);
        UserDto currentUserDto = createUserDto(userId); // Same ID

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userService.getUserDto()).thenReturn(currentUserDto);

        adminService.deleteUser(userId);

        verify(userRepository, never()).save(user);
        assertTrue(user.isEnabled());
    }



    @Test
    void testEditUser_Success() {
        Long userId = 1L;
        User user = createUser(userId, Role.ADMIN);
        UserEditDto userEditDto = createUserEditDto(userId, "Jane", false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.editUser(userEditDto);

        verify(userRepository).save(user);
        assertEquals("Jane", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals(Role.ADMIN, user.getRole());
        assertTrue(user.isEnabled());
    }

    @Test
    void testEditUser_UserNotFound() {
        Long userId = 1L;
        UserEditDto userEditDto = createUserEditDto(userId, "Jane", false);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.editUser(userEditDto));
    }

    @Test
    void testEditUser_NullDto() {
        assertThrows(NullPointerException.class, () -> adminService.editUser(null));
    }

    @Test
    void testEditUser_WithExistingUser() {
        Long userId = 1L;
        User user = createUser(userId, Role.ADMIN);
        UserEditDto userEditDto = createUserEditDto(userId, "Jane", true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.editUser(userEditDto);

        verify(userRepository).save(user);
        assertEquals("Jane", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals(Role.ADMIN, user.getRole());
        assertTrue(user.isEnabled());
    }

    @Test
    void testEditUser_WithRoleChange() {
        Long userId = 1L;
        User user = createUser(userId, Role.ADMIN);
        UserEditDto userEditDto = createUserEditDto(userId, "John", true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.editUser(userEditDto);

        verify(userRepository).save(user);
        assertEquals(Role.ADMIN, user.getRole());
    }
}
