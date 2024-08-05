package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.Comment;

import java.util.List;

public interface CommentService {

    Comment findById(Long commentId);

    List<CommentDto> getAll();

    void save(Comment comment);

    boolean delete(Long id);
}
