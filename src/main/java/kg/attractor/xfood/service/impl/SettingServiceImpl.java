package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.settings.DeadlinesDto;
import kg.attractor.xfood.dto.settings.TemplateCreateDto;
import kg.attractor.xfood.dto.settings.TemplateUpdateDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.CheckType;
import kg.attractor.xfood.model.CheckTypeFee;
import kg.attractor.xfood.model.CriteriaType;
import kg.attractor.xfood.model.Setting;
import kg.attractor.xfood.repository.SettingRepository;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {
    public final SettingRepository settingRepository;
    private final CheckTypeFeeService checkTypeFeeService;
    private final CheckTypeService checkTypeService;
    private final CriteriaService criteriaService;
    private final CriteriaTypeService criteriaTypeService;

    @Override
    public Setting getOpportunityDeadline() {
        return settingRepository.findByName("opportunity_deadline")
                .orElseThrow(() -> new NotFoundException("Таких настроек в БД не найдено"));
    }

    @Override
    public Setting getDayOffCount() {
        return settingRepository.findByName("day_off_count")
                .orElseThrow(() -> new NotFoundException("Таких настроек в БД не найдено"));
    }

    @Override
    public Setting getAppealDeadline() {
        return settingRepository.findByName("appeal_deadline")
                .orElseThrow(() -> new NotFoundException("Таких настроек в БД не найдено"));
    }

    @Override
    public void updateDeadlines(DeadlinesDto deadlines) {
        settingRepository.updateValueIntByName(deadlines.getOppDeadline(), "opportunity_deadline");
        settingRepository.updateValueIntByName(deadlines.getDayOff(), "day_off_count");
        settingRepository.updateValueIntByName(deadlines.getAppealDeadline(), "appeal_deadline");
    }

    @Override
    public boolean isCheckRecent(ChecklistShowDto dto) {
        if (dto.getEndTime() != null) {
            LocalDateTime checkEndTime = dto.getEndTime();
            LocalDateTime currentTime = LocalDateTime.now();
            int appealCreateDeadline = getAppealDeadline().getValueInt();
            return checkEndTime.isAfter(currentTime.minusDays(appealCreateDeadline));
        }
        return false;
    }

    @Override
    public boolean isAvailableToChange(LocalDate monday) {
        LocalDate today = LocalDate.now();
        int opportunityChangeDeadline = getOpportunityDeadline().getValueInt();
        return today.isBefore(monday.minusDays(opportunityChangeDeadline));
    }

    @Override
    public boolean isAvailableToDayOff(Map<String, OpportunityDto> opportunitiesMap) {
        int weeklyDayOffCount = 0;
        int dayOffLimit = getDayOffCount().getValueInt();
        for (OpportunityDto dto : opportunitiesMap.values()) {
            if (dto != null) {
                if (dto.getIsDayOff()) {
                    weeklyDayOffCount++;
                }
            }
        }
        return weeklyDayOffCount < dayOffLimit;
    }

    @Override
    public void createTemplate(TemplateCreateDto templateCreateDto) {
        BigDecimal templatePrice = BigDecimal.valueOf(templateCreateDto.getTemplatePrice());
        CheckType checkType = CheckType.builder()
                .name(templateCreateDto.getTemplateName())
                .build();
        checkTypeService.save(checkType);
        CheckTypeFee checkTypeFee = CheckTypeFee.builder()
                .checkType(checkType)
                .fees(templatePrice)
                .enabled(true)
                .createdDate(LocalDateTime.now())
                .build();
        checkTypeFeeService.save(checkTypeFee);
        templateCreateDto.getCriteriaMaxValueDtoList().removeIf(criteriaMaxValueDto -> criteriaMaxValueDto.getCriteriaId() == null);
        for (CriteriaMaxValueDto criteriaMaxValueDto : templateCreateDto.getCriteriaMaxValueDtoList()){
            CriteriaType criteriaType = CriteriaType.builder()
                    .type(checkType)
                    .maxValue(criteriaMaxValueDto.getMaxValue())
                    .criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
                    .build();
            criteriaTypeService.save(criteriaType);
        }
    }

    @Override
    public TemplateCreateDto getTemplate(Long id) {
        TemplateCreateDto templateCreateDto = new TemplateCreateDto();
        CheckType checkType = checkTypeService.getById(id);
        templateCreateDto.setTemplateName(checkType.getName());
        templateCreateDto.setTemplatePrice(checkTypeFeeService.getFeesByCheckTypeId(checkType.getId()).doubleValue());
        List<CriteriaType> criteriaTypes = criteriaTypeService.findAllByTypeId(checkType.getId());
        List<CriteriaMaxValueDto> criteriaMaxValueDtoList = new ArrayList<>();
        for(CriteriaType criteriaType : criteriaTypes){
            criteriaMaxValueDtoList.add(CriteriaMaxValueDto.builder()
                            .criteriaId(criteriaType.getCriteria().getId())
                            .description(criteriaType.getCriteria().getDescription())
                            .zone(criteriaType.getCriteria().getZone().getName())
                            .section(criteriaType.getCriteria().getSection().getName())
                            .maxValue(criteriaType.getMaxValue())
                    .build());
        }
        templateCreateDto.setCriteriaMaxValueDtoList(criteriaMaxValueDtoList);
        return templateCreateDto;
    }

    @Override
    public void updateTemplate(Long id, TemplateUpdateDto templateUpdateDto) {
        CheckType checkType = checkTypeService.getById(id);
        BigDecimal fee = BigDecimal.valueOf(templateUpdateDto.getTemplatePrice());
        checkType.setName(templateUpdateDto.getTemplateName());
        checkTypeService.save(checkType);
        CheckTypeFee oldFee = checkTypeFeeService.getCheckTypeFeeByTypeId(checkType.getId());
        oldFee.setEnabled(false);
        checkTypeFeeService.save(oldFee);
        CheckTypeFee checkTypeFee = CheckTypeFee
                .builder()
                .checkType(checkType)
                .fees(fee)
                .enabled(true)
                .createdDate(LocalDateTime.now())
                .build();
        checkTypeFeeService.save(checkTypeFee);
        List<CriteriaType> criteriaTypes = criteriaTypeService.findAllByTypeId(checkType.getId());
        for(CriteriaType criteriaType : criteriaTypes){
            checkTypeService.delete(criteriaType);
        }
        templateUpdateDto.getCriteriaMaxValueDtoList().removeIf(criteriaMaxValueDto -> criteriaMaxValueDto.getCriteriaId() == null);
        for(CriteriaMaxValueDto criteriaMaxValueDto : templateUpdateDto.getCriteriaMaxValueDtoList()){
            CriteriaType criteriaType = CriteriaType.builder()
                    .criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
                    .maxValue(criteriaMaxValueDto.getMaxValue())
                    .type(checkType)
                    .build();
            log.info("Saving CriteriaType: " + criteriaType.getCriteria().getDescription());
            criteriaTypeService.save(criteriaType);
        }
    }
}
