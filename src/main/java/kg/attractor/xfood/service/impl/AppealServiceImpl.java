package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.appeal.AppealSupervisorReviewDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaSupervisorReviewDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.CheckListsCriteriaComment;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private final AppealRepository appealRepository;
    private final CheckListCriteriaCommentService commentService;

    @Override
    public void create(CreateAppealDto createDto) {

    }

    @Override
    public AppealSupervisorReviewDto getAppealById(Long id) {
        Appeal appeal = appealRepository.findAppealById(id).orElseThrow(()-> new NoSuchElementException("Апелляция с айди "+id+"не найденно"));
        Criteria criteria = appeal.getCheckListsCriteria().getCriteria();
        Pizzeria pizzeria = appeal.getCheckListsCriteria().getChecklist().getWorkSchedule().getPizzeria();
        //  List<CheckListsCriteriaComment> commentList = commentService.findAllByCriteriaIdAndCheckListId(criteria.getId(), appeal.getCheckListsCriteria().getChecklist().getId());

        return AppealSupervisorReviewDto.builder()
                .email(appeal.getEmail())
                .comment(appeal.getComment())
                .files(appeal.getFiles())
                .checkListsCriteria(CheckListCriteriaSupervisorReviewDto.builder()
                        .criteria(CriteriaSupervisorShowDto.builder()
                                .section(criteria.getSection().getName())
                                .zone(criteria.getZone().getName())
                                .id(criteria.getId())
                                .coefficient(criteria.getCoefficient())
                                .description(criteria.getDescription())
                                .maxValue(appeal.getCheckListsCriteria().getMaxValue())
                                .value(appeal.getCheckListsCriteria().getValue())
                                .build())
                        .localDate(appeal.getCheckListsCriteria().getChecklist().getOpportunity().getDate())
                        .pizzeria(PizzeriaDto.builder().name(pizzeria.getName()).build())
                        .build())
                .build();
    }
}
