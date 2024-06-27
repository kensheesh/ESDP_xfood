package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaDto;
import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.repository.CriteriaRepository;
import kg.attractor.xfood.repository.SectionRepository;
import kg.attractor.xfood.repository.ZoneRepository;
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
    private final ChecklistCriteriaRepository checkListCriteriaRepository;
    private final CriteriaService criteriaService;
    private final CheckListService checkListService;
    private final DtoBuilder dtoBuilder;
    private final CriteriaRepository criteriaRepository;
    private final SectionRepository sectionRepository;
    private final ZoneRepository zoneRepository;

    @Override
    public void save(List<SaveCriteriaDto> saveCriteriaDto) {

        saveCriteriaDto.forEach(c -> {
            try {
                int maxValue = (c.getMaxValue() != null) ? c.getMaxValue() : 0;

                Long checkListId = checkListService.getModelCheckListById(c.getCheckListId()).getId();
                Long criteriaId = criteriaService.getCriteriaById(c.getCriteriaId()).getId();

                CheckListsCriteria optional = isPresentOptional(criteriaId, checkListId);

                if (optional != null) {
                    optional.setMaxValue(maxValue);
                    optional.setValue(c.getValue());

                    checkListCriteriaRepository.save(optional);
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

    @Override
    public void save(CheckListsCriteria checkListsCriteria) {
        checkListCriteriaRepository.save(checkListsCriteria);
        log.info("Saved checklist criteria: {}, {}, {}", checkListsCriteria.getCriteria(), checkListsCriteria.getMaxValue(), checkListsCriteria.getChecklist());
    }

    @Override
    public CheckListCriteriaDto createNewFactor(SaveCriteriaDto saveCriteriaDto) {
        CheckListsCriteria checkListsCriteria = isPresentOptional(saveCriteriaDto.getCriteriaId(), saveCriteriaDto.getCheckListId());
        if (checkListsCriteria == null) {
            CheckListsCriteria criteria = CheckListsCriteria.builder()
                    .maxValue(0)
                    .checklist(checkListService.getModelCheckListById(saveCriteriaDto.getCheckListId()))
                    .criteria(criteriaService.getCriteriaById(saveCriteriaDto.getCriteriaId()))
                    .value(saveCriteriaDto.getValue())
                    .build();
            CheckListsCriteria model =  checkListCriteriaRepository.save(criteria);
            return dtoBuilder.buildCheckListCriteriaDto(model);
        }

        throw new IllegalArgumentException("Такой wow-фактор уже существует! Вы можете добавить только один раз!");
    }

    @Override
    public void deleteFactor(Long id, Long checkListId) {
        CheckListsCriteria checkListsCriteria = isPresentOptional(id, checkListId);
        if(checkListsCriteria != null) checkListCriteriaRepository.delete(checkListsCriteria);
    }

    @Override
    public CheckListCriteriaDto createCritFactor(SaveCriteriaDto saveCriteriaDto, String description) {
        if(description.isEmpty()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        Criteria criteria = Criteria.builder()
                .description(description)
                .section(sectionRepository.findById(1L).get())
                .zone(zoneRepository.findById(9L).get())
                .coefficient(1)
                .build();

        Criteria newCriteria = criteriaRepository.save(criteria);
        saveCriteriaDto.setCriteriaId(newCriteria.getId());
        saveCriteriaDto.setValue(-8);

        return createNewFactor(saveCriteriaDto);
    }

    private CheckListsCriteria isPresentOptional(Long criteriaId, Long checkListId) {
        Optional<CheckListsCriteria> optional = checkListCriteriaRepository
                .findByCheckListIdAndCriteriaId(checkListId, criteriaId);

        return optional.orElse(null);
    }
}
