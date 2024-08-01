package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.Comment;
import kg.attractor.xfood.repository.CommentRepository;
import kg.attractor.xfood.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
