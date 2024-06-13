package kg.attractor.xfood.service.impl;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.IncorrectDateException;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
	
	private final CheckListRepository checkListRepository;
	private final WorkScheduleService workScheduleService;
	private final DtoBuilder dtoBuilder;
	private final UserService userService;
	private final OpportunityService opportunityService;
	private final CheckTypeService checkTypeService;
	private final CriteriaService criteriaService;
	private final CriteriaTypeService criteriaTypeService;
	private final CheckListCriteriaService checkListsCriteriaService;

	@Override
	public List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status) {
		return checkListRepository.findCheckListByExpertEmailAndStatus(username, status)
				.stream()
				.map(dtoBuilder :: buildChecklistDto)
				.toList();
	}
	
	@Override
	public CheckListResultDto getResult(Long checkListId) {
		return dtoBuilder.buildCheckListResultDto(
				checkListRepository.findByIdAndStatus(checkListId, Status.DONE)
						.orElseThrow(() -> new NotFoundException("Check list not found"))
		);
	}

	@Override
	@Transactional
	public void create(CheckListSupervisorCreateDto createDto) {
		log.info(createDto.toString());
		if (createDto.getStartTime().isAfter(createDto.getEndTime())) {
			throw new IncorrectDateException("Start time cannot be after end time");
		}
		WorkSchedule workSchedule = workScheduleService.findWorkScheduleByManagerAndDate(createDto.getManagerId(), createDto.getDate());
		log.info(workSchedule.getStartTime().toString());
		log.info(createDto.getEndTime().toString());
		if (createDto.getEndTime().isBefore(workSchedule.getStartTime())) {
			throw new IncorrectDateException("Start time cannot be after end time of expert");
		}
		Opportunity opportunity = Opportunity.builder()
				.user(userService.findById(createDto.getExpertId()))
				.date(createDto.getDate())
				.startTime(createDto.getStartTime())
				.endTime(createDto.getEndTime())
				.build();
		opportunityService.save(opportunity);

		Long mnId = workSchedule.getManager().getId();
		Long expertId=opportunity.getUser().getId();


		int checkListId = checkListRepository.saveChecklist(expertId, mnId, Status.NEW.getStatus());
		long parsedCheckListId = Long.parseLong(String.valueOf(checkListId));
		CheckList checkList = checkListRepository.findById(parsedCheckListId).orElseThrow();

		log.info("citerion and max values {}", createDto.getCriteriaMaxValueDtoList());

		for (CriteriaMaxValueDto criteriaMaxValueDto : createDto.getCriteriaMaxValueDtoList()){
			CriteriaType criteriaType = CriteriaType.builder()
					.type(checkTypeService.getById(createDto.getCheckTypeId()))
					.criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
					.build();
			criteriaTypeService.save(criteriaType);
			log.error("Max value : " + criteriaMaxValueDto.getMaxValue());
			CheckListsCriteria checkListsCriteria = CheckListsCriteria.builder()
					.checklist(checkList)
					.criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
					.maxValue(criteriaMaxValueDto.getMaxValue())
					.value(1)
					.build();
			checkListsCriteriaService.save(checkListsCriteria);

		}

	}

}
