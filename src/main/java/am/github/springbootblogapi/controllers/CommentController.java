package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.CommentDTO;
import am.github.springbootblogapi.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/get/{postId}")
    public List<CommentDTO> getAll(@PathVariable(name = "postId") Long postId) {
        return commentService.getAll(postId);
    }

    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentDTO> create(
            @PathVariable(name = "postId") Long postId,
            @Valid @RequestBody CommentDTO commentDto
    ) {
        return new ResponseEntity<CommentDTO>(this.commentService.create(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<CommentDTO> single(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(commentService.single(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CommentDTO> update(
            @Valid @RequestBody CommentDTO commentDto,
            @PathVariable(name = "id") Long id
    ) {
        return new ResponseEntity<CommentDTO>(this.commentService.update(commentDto, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<String>("Comment deleted successfully.", HttpStatus.OK);
    }
}
