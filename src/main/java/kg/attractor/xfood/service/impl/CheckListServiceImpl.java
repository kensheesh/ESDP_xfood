package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checks.CheckListDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.CheckListCriteriaRepository;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;
    private final CheckListCriteriaRepository checkListCriteriaRepository;

    @Override
    public CheckListDto getCheckListById(Long id) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow();
        List<CheckListsCriteria> checkListsCriteria = checkListCriteriaRepository.findByChecklistId(id);

        return CheckListDto.builder()
                .workSchedule(checkList.getWorkSchedule())
                .checkListsCriteriaList(checkListsCriteria)
                .build();
    }
}
