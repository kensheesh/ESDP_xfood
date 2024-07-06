package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.impl.CheckListServiceImpl;
import kg.attractor.xfood.service.impl.DtoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CheckListServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CheckListRepository checkListRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @InjectMocks
    private CheckListServiceImpl checkListService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("test@example.com")
                .password("password")
                .roles(String.valueOf(Role.EXPERT))
                .build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    void testGetAnalyticsForExpert() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.EXPERT);

        CheckList checkList = new CheckList();
        checkList.setStatus(Status.DONE);

        List<CheckList> checkLists = Collections.singletonList(checkList);
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE))).thenReturn(checkLists);
        when(dtoBuilder.buildCheckListAnalyticsDto(any(CheckList.class))).thenReturn(checkListAnalyticsDto);

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE));
        verify(dtoBuilder, times(1)).buildCheckListAnalyticsDto(any(CheckList.class));
    }

    @Test
    void testGetAnalyticsForAdmin() {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setRole(Role.ADMIN);

        CheckList checkList = new CheckList();
        checkList.setStatus(Status.DONE);

        List<CheckList> checkLists = Collections.singletonList(checkList);
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findByStatus(eq(Status.DONE))).thenReturn(checkLists);
        when(dtoBuilder.buildCheckListAnalyticsDto(any(CheckList.class))).thenReturn(checkListAnalyticsDto);

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findByStatus(eq(Status.DONE));
        verify(dtoBuilder, times(1)).buildCheckListAnalyticsDto(any(CheckList.class));
    }

    @Test
    void testGetAnalyticsForSupervisor() {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setRole(Role.SUPERVISOR);

        CheckList checkList = new CheckList();
        checkList.setStatus(Status.DONE);

        List<CheckList> checkLists = Collections.singletonList(checkList);
        CheckListAnalyticsDto checkListAnalyticsDto = new CheckListAnalyticsDto();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findByStatus(eq(Status.DONE))).thenReturn(checkLists);
        when(dtoBuilder.buildCheckListAnalyticsDto(any(CheckList.class))).thenReturn(checkListAnalyticsDto);

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findByStatus(eq(Status.DONE));
        verify(dtoBuilder, times(1)).buildCheckListAnalyticsDto(any(CheckList.class));
    }

    @Test
    void testGetAnalyticsEmptyResult() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.EXPERT);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE))).thenReturn(Collections.emptyList());

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", null, null);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE));
        verify(dtoBuilder, never()).buildCheckListAnalyticsDto(any());
    }

    @Test
    void testGetAnalyticsRevertedDates_ReturnEmptyResult() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.EXPERT);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(checkListRepository.findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE))).thenReturn(Collections.emptyList());

        LocalDate endDate = LocalDate.parse("2024-06-30");
        LocalDate startDate = LocalDate.parse("2024-06-15");

        List<CheckListAnalyticsDto> result = checkListService.getAnalytics("default", "default", "default", endDate, startDate);

        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(checkListRepository, times(1)).findCheckListByExpertEmailAndStatus(anyString(), eq(Status.DONE));

        verify(dtoBuilder, never()).buildCheckListAnalyticsDto(any());
    }
}