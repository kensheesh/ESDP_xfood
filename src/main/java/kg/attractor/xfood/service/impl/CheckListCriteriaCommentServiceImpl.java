package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.CheckListsCriteriaComment;
import kg.attractor.xfood.repository.ChecklistCriteriaCommentRepository;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListCriteriaCommentServiceImpl implements CheckListCriteriaCommentService {
    private final ChecklistCriteriaCommentRepository criteriaCommentRepository;

    @Override
    public void save(CheckListsCriteriaComment commentCriteria) {
        criteriaCommentRepository.save(commentCriteria);
    }


}

