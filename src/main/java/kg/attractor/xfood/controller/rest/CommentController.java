package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import kg.attractor.xfood.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("CommentRestController")
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;
    private final CheckListCriteriaCommentService checkListCriteriaCommentService;

    @GetMapping
    ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("/{checkId}/{criteriaId}")
    ResponseEntity<List<CommentDto>> getCommentsOfCriteria(@PathVariable Long checkId , @PathVariable Long criteriaId) {
        return ResponseEntity.ok(checkListCriteriaCommentService.getAllByCheckListAndCriteria(checkId, criteriaId));
    }

    @DeleteMapping("{id}/delete")
    ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (commentService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
