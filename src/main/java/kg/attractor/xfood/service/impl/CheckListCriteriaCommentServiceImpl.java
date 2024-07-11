package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.CheckListsCriteriaComment;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckListCriteriaCommentServiceImpl implements CheckListCriteriaCommentService {

    @Override
    public List<CheckListsCriteriaComment> findAllByCriteriaIdAndCheckListId(Long id, Long id1) {
        return List.of();
    }
}

