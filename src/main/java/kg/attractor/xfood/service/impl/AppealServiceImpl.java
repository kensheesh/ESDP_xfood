package kg.attractor.xfood.service.impl;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.AppealSupervisorApproveDto;
import kg.attractor.xfood.dto.appeal.AppealSupervisorReviewDto;
import kg.attractor.xfood.dto.appeal.AppealDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaSupervisorReviewDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.model.*;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.dto.appeal.DataAppealDto;
import kg.attractor.xfood.exception.AppealNotFoundException;
import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.CheckListCriteriaService;
import kg.attractor.xfood.service.FileService;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import kg.attractor.xfood.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private static final Logger log = LoggerFactory.getLogger(AppealServiceImpl.class);
    private final AppealRepository appealRepository;
    private final CheckListCriteriaCommentService commentService;
    private final CheckListCriteriaServiceImpl checkListCriteriaServiceImpl;
    private final EmailService emailService;


    private final CheckListCriteriaService checkListCriteriaService;
    private final FileService fileService;
    private final AppealRepository appealRepository;
    private final DtoBuilder dtoBuilder;

    @Override
    public Long create(DataAppealDto dto) {
        CheckListsCriteria checkListsCriteria = checkListCriteriaService
                .findByCriteriaIdAndChecklistId(dto.getCriteriaId(), dto.getCheckListId());

        Appeal appeal = Appeal.builder()
                .checkListsCriteria(checkListsCriteria)
                .comment("")
                .email("")
                .fullName("")
                .build();

        return appealRepository.save(appeal).getId();
    }

    @Override
    public AppealDto findById(Long id) {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new AppealNotFoundException("Аппеляция не найдена"));
        return dtoBuilder.buildAppealDto(appeal);
    }

    }

    @Override
    public AppealSupervisorReviewDto getAppealById(Long id) {
        Appeal appeal = appealRepository.findAppealById(id).orElseThrow(()-> new NoSuchElementException("Апелляция с айди "+id+"не найденно"));
        Criteria criteria = appeal.getCheckListsCriteria().getCriteria();
        Pizzeria pizzeria = appeal.getCheckListsCriteria().getChecklist().getWorkSchedule().getPizzeria();
        //  List<CheckListsCriteriaComment> commentList = commentService.findAllByCriteriaIdAndCheckListId(criteria.getId(), appeal.getCheckListsCriteria().getChecklist().getId());

        return AppealSupervisorReviewDto.builder()
                .id(appeal.getId())
                .email(appeal.getEmail())
                .fullName(appeal.getFullName())
                .comment(appeal.getComment())
                .files(appeal.getFiles())
                .status(appeal.getIsAccepted())
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
                        .pizzeria(pizzeria.getName())
                        .build())
                .build();
    @Override
    public void update(CreateAppealDto createAppealDto, Long id) {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new AppealNotFoundException("Аппеляция не найдена"));
        
        appeal.setEmail(createAppealDto.getEmail());
        appeal.setComment(createAppealDto.getComment());
        appeal.setFullName(createAppealDto.getFullName());
        appeal.setLinkToExternalSrc(createAppealDto.getLinkToExternalSrc());
        appeal.setTgLinkMessage(createAppealDto.getTgLinkMessage());
        
        appealRepository.save(appeal);

        List<MultipartFile> files = Arrays.asList(createAppealDto.getFiles());
        fileService.saveFiles(files, id);
    }

    @Override
    public void approve(AppealSupervisorApproveDto appealDto) throws MessagingException, UnsupportedEncodingException {
        Appeal appeal = appealRepository.findAppealById(appealDto.getAppealId()).orElseThrow(()-> new NoSuchElementException("Апелляция с айди "+appealDto.getAppealId()+" не найдено"));
        appeal.setIsAccepted(appealDto.getStatus());
        //TODO после добавления в бд колонки respond сделать созранение комментария от руководителя
        if (appealDto.getStatus()){
            CheckListsCriteria criteria = appeal.getCheckListsCriteria();
            if (criteria.getMaxValue()==null){
                criteria.setMaxValue(0);
                criteria.setValue(0);
            }
            else {
                criteria.setValue(criteria.getMaxValue());
            }
            checkListCriteriaServiceImpl.save(criteria);
        }
       appealRepository.save(appeal);
        emailService.sendEmail(appeal.getId(), appealDto.getRespond());
    }
}
