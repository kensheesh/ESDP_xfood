package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.IncorrectDateException;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.repository.WorkScheduleRepository;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	public void create(CheckListSupervisorCreateDto createDto) {
		if (createDto.getStartTime().isAfter(createDto.getEndTime())) {
			throw new IncorrectDateException("Start time cannot be after end time");
		}
		WorkSchedule workSchedule = workScheduleService.findWorkScheduleByManagerAndDate(createDto.getManagerId(), createDto.getDate());
		/*if (createDto.getStartTime().isAfter(workSchedule.getEndTime())) {
			throw new IncorrectDateException("Start time cannot be after end time of manager");
		}* Нужно уточнить!*/
		Opportunity opportunity = Opportunity.builder()
				.user(userService.findById(createDto.getExpertId()))
				.date(createDto.getDate())
				.startTime(createDto.getStartTime())
				.endTime(createDto.getEndTime())
				.build();
		opportunityService.save(opportunity);

		CheckList checkList = CheckList.builder()
				.opportunity(opportunity)
				.workSchedule(workSchedule)
				.status(Status.NEW)
				.build();
		checkListRepository.save(checkList);

		for (CriteriaMaxValueDto criteriaMaxValueDto : createDto.getCriteriaMaxValueDtoList()){
			CriteriaType criteriaType = CriteriaType.builder()
					.type(checkTypeService.getById(createDto.getCheckTypeId()))
					.criteria(criteriaService.findById(criteriaMaxValueDto.getCriteriaId()))
					.build();
			criteriaTypeService.save(criteriaType);

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
