package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.service.CheckListCriteriaCommentService;
import kg.attractor.xfood.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasRole('IS_AUTHENTICATED_ANONYMOUSLY')")
    ResponseEntity<List<CommentDto>> getCommentsOfCriteria(@PathVariable Long checkId , @PathVariable Long criteriaId) {
        return ResponseEntity.ok(checkListCriteriaCommentService.getAllByCheckListAndCriteria(checkId, criteriaId));
    }

    @DeleteMapping("/{commentId}/delete")
    HttpStatus deleteCommen(@PathVariable Long commentId) {
        checkListCriteriaCommentService.delete(commentId);
        return HttpStatus.OK;

    }

}
