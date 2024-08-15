package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.Comment;
import kg.attractor.xfood.repository.CommentRepository;
import kg.attractor.xfood.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setComment("Test Comment");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Comment result = commentService.findById(commentId);

        assertNotNull(result);
        assertEquals(commentId, result.getId());
        assertEquals("Test Comment", result.getComment());
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void testFindById_NotFound() {
        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> commentService.findById(commentId));

        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void testGetAll() {
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setComment("First Comment");

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setComment("Second Comment");

        List<Comment> comments = List.of(comment1, comment2);
        when(commentRepository.findAll()).thenReturn(comments);

        List<CommentDto> result = commentService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        CommentDto dto1 = result.get(0);
        assertEquals(comment1.getId(), dto1.getCommentId());
        assertEquals(comment1.getComment(), dto1.getComment());

        CommentDto dto2 = result.get(1);
        assertEquals(comment2.getId(), dto2.getCommentId());
        assertEquals(comment2.getComment(), dto2.getComment());

        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void testGetAll_EmptyList() {
        when(commentRepository.findAll()).thenReturn(new ArrayList<>());

        List<CommentDto> result = commentService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setComment("Test Comment");

        commentService.save(comment);

        verify(commentRepository, times(1)).save(comment);
    }
}