package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.CheckListsCriteriaComment;
import kg.attractor.xfood.model.Comment;
import kg.attractor.xfood.repository.ChecklistCriteriaCommentRepository;
import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListCriteriaCommentServiceImpl implements CheckListCriteriaCommentService {
    private final ChecklistCriteriaCommentRepository criteriaCommentRepository;
    private final CheckListCriteriaService criteriaService;
    private final AppealService appealService;

    @Override
    public void save(CheckListsCriteriaComment commentCriteria) {
        criteriaCommentRepository.save(commentCriteria);
    }

    @Override
    public List<CommentDto> getAllByCheckListAndCriteria(Long checkId, Long criteriaId) {
        CheckListsCriteria checkListsCriteria = criteriaService.findByCriteriaIdAndChecklistId(criteriaId, checkId);
        List<CheckListsCriteriaComment> comments =  criteriaCommentRepository.getAllByChecklistCriteria_Id(checkListsCriteria.getId());
        List<CommentDto> commentDtos = new ArrayList<>();
        for (CheckListsCriteriaComment comment : comments) {
            commentDtos.add(CommentDto.builder()
                            .checkCritCommId(comment.getId())
                            .commentId(comment.getComment().getId())
                            .comment(comment.getComment().getComment())
                            .appealed(appealService.isAppealed(comment.getComment().getId(), checkId, criteriaId))
                    .build());
        }
        log.info("comments {}", commentDtos);
        return commentDtos;
    }

    @Override
    public boolean delete( Long commentId) {
        criteriaCommentRepository.deleteById(commentId);
        return true;
    }


}

