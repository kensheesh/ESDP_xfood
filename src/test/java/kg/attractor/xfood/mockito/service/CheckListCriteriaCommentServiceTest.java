package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.model.CheckListsCriteriaComment;
import kg.attractor.xfood.model.Comment;
import kg.attractor.xfood.repository.ChecklistCriteriaCommentRepository;
import kg.attractor.xfood.service.CheckListCriteriaService;
import kg.attractor.xfood.service.impl.CheckListCriteriaCommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckListCriteriaCommentServiceTest {

    @Mock
    private ChecklistCriteriaCommentRepository criteriaCommentRepository;

    @Mock
    private CheckListCriteriaService criteriaService;

    @InjectMocks
    private CheckListCriteriaCommentServiceImpl checkListCriteriaCommentService;

    private CheckListsCriteria checkListsCriteria;
    private CheckListsCriteriaComment comment;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Comment commentEntity = new Comment();
        commentEntity.setId(1L);
        commentEntity.setComment("Sample comment");

        comment = CheckListsCriteriaComment.builder()
                .id(1L)
                .comment(commentEntity)
                .checklistCriteria(new CheckListsCriteria())
                .build();

        checkListsCriteria = new CheckListsCriteria();
        checkListsCriteria.setId(1L);

        commentDto = CommentDto.builder()
                .checkCritCommId(1L)
                .commentId(1L)
                .comment("Sample comment")
                .build();
    }

    @Test
    void saveTest() {
        checkListCriteriaCommentService.save(comment);
        verify(criteriaCommentRepository, times(1)).save(comment);
    }


    @Test
    void deleteTest() {
        Long commentId = 1L;
        checkListCriteriaCommentService.delete(commentId);

        verify(criteriaCommentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void ifExistsTest() {
        when(criteriaCommentRepository.existsByChecklistCriteria_IdAndComment_Id(1L, 1L)).thenReturn(true);

        boolean exists = checkListCriteriaCommentService.ifExists(1L, 1L);

        assertTrue(exists);

        verify(criteriaCommentRepository, times(1)).existsByChecklistCriteria_IdAndComment_Id(1L, 1L);
    }

    @Test
    void ifNotExistsTest() {
        when(criteriaCommentRepository.existsByChecklistCriteria_IdAndComment_Id(1L, 1L)).thenReturn(false);

        boolean exists = checkListCriteriaCommentService.ifExists(1L, 1L);

        assertFalse(exists);

        verify(criteriaCommentRepository, times(1)).existsByChecklistCriteria_IdAndComment_Id(1L, 1L);
    }
}