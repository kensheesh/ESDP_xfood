package kg.attractor.xfood.service;

import kg.attractor.xfood.model.CheckListsCriteriaComment;

import java.util.List;

public interface CheckListCriteriaCommentService {
    List<CheckListsCriteriaComment> findAllByCriteriaIdAndCheckListId(Long id, Long id1);
}
