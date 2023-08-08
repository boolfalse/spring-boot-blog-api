package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.CommentDTO;
import am.github.springbootblogapi.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment CRUD Resource API")
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Get Post Comments", description = "Get all the comments by a post.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/get/{postId}")
    public List<CommentDTO> getAll(@PathVariable(name = "postId") Long postId) {
        return commentService.getAll(postId);
    }

    @Operation(summary = "Add Comment", description = "Comment to a post and get the created comment as a result.")
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentDTO> create(
            @PathVariable(name = "postId") Long postId,
            @Valid @RequestBody CommentDTO commentDto
    ) {
        CommentDTO createdCommentDto = this.commentService.create(postId, commentDto);

        return new ResponseEntity<CommentDTO>(createdCommentDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Comment", description = "Get a single comment.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/single/{id}")
    public ResponseEntity<CommentDTO> single(@PathVariable(name = "id") Long id) {
        CommentDTO commentDto = commentService.single(id);

        return ResponseEntity.ok(commentDto);
    }

    @Operation(summary = "Update Comment", description = "Update a comment and get the updated comment as a result.")
    @ApiResponse(responseCode = "202", description = "HTTP Status ACCEPTED")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<CommentDTO> update(
            @Valid @RequestBody CommentDTO commentDto,
            @PathVariable(name = "id") Long id
    ) {
        CommentDTO updatedCommentDto = this.commentService.update(commentDto, id);

        return new ResponseEntity<CommentDTO>(updatedCommentDto, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update Comment", description = "Delete comment.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        commentService.delete(id);

        return new ResponseEntity<String>("Comment deleted successfully.", HttpStatus.OK);
    }
}
