package kg.attractor.xfood.service.impl;

import jakarta.mail.MessagingException;
import kg.attractor.xfood.dto.appeal.*;
import kg.attractor.xfood.dto.checklist_criteria.CheckListCriteriaSupervisorReviewDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.exception.AppealNotFoundException;
import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private static final Logger log = LoggerFactory.getLogger(AppealServiceImpl.class);
    private final CheckListCriteriaServiceImpl checkListCriteriaServiceImpl;
    private final CheckListCriteriaService checkListCriteriaService;
    private final CheckListCriteriaCommentService commentService;
    private final AppealRepository appealRepository;
    private final EmailService emailService;
    private final FileService fileService;
    private final DtoBuilder dtoBuilder;

    @Override
    public void update(CreateAppealDto createAppealDto, Long id) {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new AppealNotFoundException("Аппеляция не найдена"));

        appeal.setEmail(createAppealDto.getEmail());
        appeal.setComment_expert(createAppealDto.getComment());
        appeal.setFullName(createAppealDto.getFullName());
        appeal.setLinkToExternalSrc(createAppealDto.getLinkToExternalSrc());
        appeal.setTgLinkMessage(createAppealDto.getTgLinkMessage());

        appealRepository.save(appeal);
        if(createAppealDto.getFiles() != null) {
            List<MultipartFile> files = Arrays.asList(createAppealDto.getFiles());
            fileService.saveFiles(files, id);
        }
    }

    @Override
    public AppealSupervisorReviewDto getAppealById(Long id) {
          Appeal appeal = appealRepository.findAppealById(id).orElseThrow(() -> new NoSuchElementException("Апелляция с айди " + id + "не найденно"));
          Criteria criteria = appeal.getCheckListsCriteria().getCriteria();
          Pizzeria pizzeria = appeal.getCheckListsCriteria().getChecklist().getWorkSchedule().getPizzeria();
          //  List<CheckListsCriteriaComment> commentList = commentService.findAllByCriteriaIdAndCheckListId(criteria.getId(), appeal.getCheckListsCriteria().getChecklist().getId());
          return AppealSupervisorReviewDto.builder()
                  .checkListUuid(appeal.getCheckListsCriteria().getChecklist().getUuidLink())
                  .id(appeal.getId())
                  .email(appeal.getEmail())
                  .fullName(appeal.getFullName())
                  .comment(appeal.getComment_expert())
                  .files(appeal.getFiles())
                  .status(appeal.getIsAccepted())
                  .localDate(appeal.getCheckListsCriteria().getChecklist().getEndTime())
                  .respond(appeal.getComment_supervisor())
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
                          .pizzeria(pizzeria.getName())
                          .build())
                  .build();
    }

    @Override
    public Long create(DataAppealDto dto) {
        CheckListsCriteria checkListsCriteria = checkListCriteriaService
                .findByCriteriaIdAndChecklistId(dto.getCriteriaId(), dto.getCheckListId());

        Appeal appeal = Appeal.builder()
                .checkListsCriteria(checkListsCriteria)
//                .comment("")
                .email("")
                .fullName("")
                .build();

        return appealRepository.save(appeal).getId();
    }

    @Override
    public AppealDto findById(Long id) {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new AppealNotFoundException("Апелляция не найдена"));
        return dtoBuilder.buildAppealDto(appeal);
    }

    public void approve(AppealSupervisorApproveDto appealDto) throws MessagingException, UnsupportedEncodingException {
        Appeal appeal = appealRepository.findAppealById(appealDto.getAppealId()).orElseThrow(() -> new NoSuchElementException("Апелляция с айди " + appealDto.getAppealId() + " не найдено"));
        appeal.setIsAccepted(appealDto.getStatus());
        appeal.setComment_supervisor(appealDto.getRespond());

        if (appealDto.getStatus()) {
            CheckListsCriteria criteria = appeal.getCheckListsCriteria();
            if (criteria.getMaxValue() == null) {
                Criteria c = appeal.getCheckListsCriteria().getCriteria();
                criteria.setValue(criteria.getValue() + c.getCoefficient());
                criteria.setMaxValue(0);
            } else {
                criteria.setValue(criteria.getValue()+ criteria.getMaxValue());
            }
            checkListCriteriaServiceImpl.save(criteria);
        }
        appealRepository.save(appeal);
        emailService.sendEmail(appeal.getId(), appealDto.getRespond());
    }

    @Override
    public Integer getAmountOfNewAppeals() {
        return appealRepository.countAllByIsAcceptedNull();
    }

    @Override
    public Page<AppealListDto> getAllByStatus(Boolean isAccepted, int page) {
        Page<Appeal> appeals = appealRepository.findAllByIsAccepted(isAccepted, PageRequest.of(page - 1, 6));
        return appeals.map(dtoBuilder::buildAppealsListDto);
    }
}