package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.checklist.CheckListRewardDto;
import kg.attractor.xfood.dto.user.ExpertRewardDto;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.impl.RewardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RewardServiceTest {

    @Mock
    private CheckListService checkListService;

    @InjectMocks
    private RewardServiceImpl rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExpertReward() {
        String expertEmail = "expert@example.com";
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 31);
        String pizzeriaName = "Pizzeria";

        CheckListRewardDto checkListRewardDto = new CheckListRewardDto();
        checkListRewardDto.setSumRewards(100.0);

        when(checkListService.getChecklistRewardsByExpert(expertEmail, startDate.atStartOfDay(), endDate.atStartOfDay(), pizzeriaName))
                .thenReturn(Collections.singletonList(checkListRewardDto));

        ExpertRewardDto result = rewardService.getExpertReward(expertEmail, pizzeriaName, startDate, endDate);

        assertNotNull(result);
        assertEquals(100.0, result.getSumRewards());
        assertEquals(1, result.getCountChecks());
        assertFalse(result.getCheckListRewards().isEmpty());
    }

    @Test
    void testGetCheckListReward() {
        String expertEmail = "expert@example.com";
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 31);
        String pizzeriaName = "Pizzeria";

        CheckListRewardDto checkListRewardDto = new CheckListRewardDto();
        checkListRewardDto.setSumRewards(100.0);

        when(checkListService.getChecklistRewardsByExpert(expertEmail, startDate.atStartOfDay(), endDate.atStartOfDay(), pizzeriaName))
                .thenReturn(Collections.singletonList(checkListRewardDto));

        List<CheckListRewardDto> result = rewardService.getCheckListReward(expertEmail, startDate, endDate, pizzeriaName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getSumRewards());
    }

}