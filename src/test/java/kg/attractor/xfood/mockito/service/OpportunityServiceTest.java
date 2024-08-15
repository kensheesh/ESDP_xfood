package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.opportunity.WeeklyOpportunityShowDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.OpportunityRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.OpportunityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpportunityServiceTest {

    @Mock
    private OpportunityRepository opportunityRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @InjectMocks
    private OpportunityServiceImpl opportunityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Opportunity opportunity = new Opportunity();
        opportunity.setId(1L);

        when(opportunityRepository.save(opportunity)).thenReturn(opportunity);

        Long resultId = opportunityService.save(opportunity);

        assertEquals(1L, resultId);
        verify(opportunityRepository, times(1)).save(opportunity);
    }


    @Test
    void testGetWeeklyOpportunities() {
        long week = 0;
        LocalDateTime monday = LocalDateTime.now().with(DayOfWeek.MONDAY);

        User user = new User();
        user.setId(1L);

        Opportunity opportunity = new Opportunity();
        opportunity.setId(1L);
        opportunity.setDate(monday.toLocalDate());
        opportunity.setUser(user);

        List<Opportunity> opportunities = Collections.singletonList(opportunity);

        when(opportunityRepository.findByDateBetweenOrderByUser_SurnameAsc(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(opportunities);
        when(dtoBuilder.buildExpertShowDto(any(User.class))).thenReturn(new ExpertShowDto()); // Mocked

        List<WeeklyOpportunityShowDto> result = opportunityService.getWeeklyOpportunities(week, "");

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
