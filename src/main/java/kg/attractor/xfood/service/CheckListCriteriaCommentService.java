package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.CheckListsCriteriaComment;

import java.util.List;

public interface CheckListCriteriaCommentService {
    void save(CheckListsCriteriaComment commentCriteria);

    List<CommentDto> getAllByCheckListAndCriteria(Long checkId, Long criteriaId);

    boolean delete(Long checkId, Long criteriaId, Long commentId);
}
