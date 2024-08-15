package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.model.Comment;
import kg.attractor.xfood.repository.CommentRepository;
import kg.attractor.xfood.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long commentId) {
      return commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("Комментарий не найден"));
    }

    @Override
    public List<CommentDto> getAll() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(CommentDto.builder()
                            .comment(comment.getComment())
                            .commentId(comment.getId())
                    .build());
        }
        log.info("comments : {}", commentDtos);
        return commentDtos;
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}