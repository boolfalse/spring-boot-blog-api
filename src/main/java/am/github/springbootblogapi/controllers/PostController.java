package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.payloads.PostResponse;
import am.github.springbootblogapi.services.PostService;
import am.github.springbootblogapi.config.AppConstants;
import am.github.springbootblogapi.validations.PostFilteredParameters;
import am.github.springbootblogapi.validations.PostValidationFilter;
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

@Tag(name = "Post CRUD Resource API")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Get Posts", description = "Get posts with pagination.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping
    public PostResponse getAll(
            @RequestParam(value = "page", defaultValue = AppConstants.APP_DEFAULT_PAGE, required = false) String page,
            @RequestParam(value = "per_page", defaultValue = AppConstants.APP_DEFAULT_PER_PAGE, required = false) String per_page,
            @RequestParam(value = "sort_by", defaultValue = AppConstants.APP_DEFAULT_SORT_BY, required = false) String sort_by,
            @RequestParam(value = "order_by", defaultValue = AppConstants.APP_DEFAULT_ORDER_BY, required = false) String order_by
    ) {
        PostFilteredParameters filtered = PostValidationFilter.getAll(page, per_page, sort_by, order_by);

        return postService.getAll(
                filtered.getPageNumber(),
                filtered.getPerPageNumber(),
                filtered.getSortBy(),
                filtered.getOrderBy()
        );
    }

    @Operation(summary = "Create Post", description = "Create a post and get the created post as a result.")
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDto) {
        PostDTO createdPostDto = this.postService.create(postDto);

        return new ResponseEntity<PostDTO>(createdPostDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Post", description = "Get a single post.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> single(@PathVariable(name = "id") Long id) {
        PostDTO postDto = postService.single(id);

        return ResponseEntity.ok(postDto);
    }

    @Operation(summary = "Update Post", description = "Update post and get the updated post as a result.")
    @ApiResponse(responseCode = "202", description = "HTTP Status ACCEPTED")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(
            @Valid @RequestBody PostDTO postDto,
            @PathVariable(name = "id") Long id
    ) {
        PostDTO updatedPostDto = this.postService.update(postDto, id);

        return new ResponseEntity<PostDTO>(updatedPostDto, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete Post", description = "Delete post.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        postService.delete(id);

        return new ResponseEntity<String>("Post deleted successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Get Category Posts", description = "Get all the posts by a category.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/{id}/category")
    public ResponseEntity<List<PostDTO>> getByCategory(@PathVariable(name = "id") int categoryId) {
        List<PostDTO> postDTOs = postService.getCategoryPosts(categoryId);

        return ResponseEntity.ok(postDTOs);
    }
}
