package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.payloads.PostResponse;
import am.github.springbootblogapi.services.PostService;
import am.github.springbootblogapi.config.AppConstants;
import am.github.springbootblogapi.validations.PostFilteredParameters;
import am.github.springbootblogapi.validations.PostValidationFilter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

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

    @PreAuthorize("") // hasRole('ADMIN')
    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDto) {
        PostDTO createdPostDto = this.postService.create(postDto);

        return new ResponseEntity<PostDTO>(createdPostDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> single(@PathVariable(name = "id") Long id) {
        PostDTO postDto = postService.single(id);

        return ResponseEntity.ok(postDto);
    }

    @PreAuthorize("") // hasRole('ADMIN')
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(
            @Valid @RequestBody PostDTO postDto,
            @PathVariable(name = "id") Long id
    ) {
        PostDTO updatedPostDto = this.postService.update(postDto, id);

        return new ResponseEntity<PostDTO>(updatedPostDto, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("") // hasRole('ADMIN')
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        postService.delete(id);

        return new ResponseEntity<String>("Post deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/{id}/category")
    public ResponseEntity<List<PostDTO>> getByCategory(@PathVariable(name = "id") int categoryId) {
        List<PostDTO> postDTOs = postService.getCategoryPosts(categoryId);

        return ResponseEntity.ok(postDTOs);
    }
}
