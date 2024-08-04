package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.CheckListsCriteriaComment;
import kg.attractor.xfood.repository.ChecklistCriteriaCommentRepository;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListCriteriaCommentServiceImpl implements CheckListCriteriaCommentService {
    private final ChecklistCriteriaCommentRepository criteriaCommentRepository;
    private final CheckListCriteriaService criteriaService;

    @Override
    public void save(CheckListsCriteriaComment commentCriteria) {
        criteriaCommentRepository.save(commentCriteria);
    }

    @Override
    public List<CommentDto> getAllByCheckListAndCriteria(Long checkId, Long criteriaId) {
        CheckListsCriteria checkListsCriteria = criteriaService.findByCriteriaIdAndChecklistId(checkId, criteriaId);
        List<CheckListsCriteriaComment> comments =  criteriaCommentRepository.getAllByChecklistCriteria_Id(criteriaId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (CheckListsCriteriaComment comment : comments) {
            commentDtos.add(CommentDto.builder()
                            .commentId(comment.getId())
                            .comment(comment.getComment().getComment())
                    .build());
        }
        return commentDtos;
    }


}

