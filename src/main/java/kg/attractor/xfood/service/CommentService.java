package kg.attractor.xfood.service;

import kg.attractor.xfood.model.Comment;

public interface CommentService {

    Comment findById(Long commentId);
}
