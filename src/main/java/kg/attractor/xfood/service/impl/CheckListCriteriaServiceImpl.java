package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.CheckListCriteriaRepository;
import kg.attractor.xfood.service.CheckListCriteriaService;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListCriteriaServiceImpl implements CheckListCriteriaService {
    private final CheckListCriteriaRepository checkListCriteriaRepository;
    private final CriteriaService criteriaService;
    private final CheckListService checkListService;

    @Override
    public void save(List<SaveCriteriaDto> saveCriteriaDto) {

        saveCriteriaDto.forEach(c -> {
            try {
                int maxValue = (c.getMaxValue() != null) ? c.getMaxValue() : 0;

                Long checkListId = checkListService.getModelCheckListById(c.getCheckListId()).getId();
                Long criteriaId = criteriaService.getCriteriaById(c.getCriteriaId()).getId();

                Optional<CheckListsCriteria> optional = checkListCriteriaRepository
                        .findByCheckListIdAndCriteriaId(checkListId, criteriaId);

                if(optional.isPresent()) {
                    CheckListsCriteria criteria = optional.get();
                    criteria.setMaxValue(maxValue);
                    criteria.setValue(c.getValue());

                    checkListCriteriaRepository.save(criteria);
                } else {
                    CheckListsCriteria checkListsCriteria = CheckListsCriteria.builder()
                            .value(c.getValue())
                            .criteria(criteriaService.getCriteriaById(c.getCriteriaId()))
                            .checklist(checkListService.getModelCheckListById(c.getCheckListId()))
                            .maxValue(maxValue)
                            .build();

                    checkListCriteriaRepository.save(checkListsCriteria);
                }
            } catch (Exception e) {
                System.err.println("Error processing criteria: " + e.getMessage());
                e.printStackTrace();
            }
        });

    }
}
