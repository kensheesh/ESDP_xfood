package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.repository.OpportunityRepository;
import kg.attractor.xfood.service.impl.OpportunityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OpportunityServiceTest {
    @InjectMocks
    private OpportunityServiceImpl opportunityService;

    @Mock
    private OpportunityRepository opportunityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOppotunitiesByDate() {
        LocalDate date = LocalDate.of(2024, 7, 17);
        when(opportunityRepository.findByDateOrderByUser_SurnameAsc(date))
        opportunityRepository.findByDateOrderByUser_SurnameAsc(date);

    }
}
