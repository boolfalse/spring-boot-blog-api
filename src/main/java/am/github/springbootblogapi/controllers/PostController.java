package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.payloads.PostResponse;
import am.github.springbootblogapi.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public PostResponse getAll(
            @RequestParam(value = "page", defaultValue = "1", required = false) String page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) String per_page,
            @RequestParam(value = "sort_by", defaultValue = "id", required = false) String sort_by
    ) {
        // TODO: separate validation part
        int pageNumber;
        int perPageNumber;
        String sortBy;
        try {
            pageNumber = Integer.parseInt(page);
            perPageNumber = Integer.parseInt(per_page);
            if (pageNumber < 1 || pageNumber > 1000 || perPageNumber < 1 || perPageNumber > 100) {
                pageNumber = 1;
                perPageNumber = 10;
            }

            String[] fields = {"id", "title", "description"};
            if (Arrays.asList(fields).contains(sort_by)) {
                sortBy = sort_by;
            } else {
                sortBy = "id";
            }
        } catch (NumberFormatException e) {
            pageNumber = 1;
            perPageNumber = 10;
            sortBy = "id";
        }

        return postService.getAll(pageNumber, perPageNumber, sortBy);
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDto) {
        return new ResponseEntity<PostDTO>(this.postService.create(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> single(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.single(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(
            @RequestBody PostDTO postDto,
            @PathVariable(name = "id") Long id
    ) {
        return new ResponseEntity<PostDTO>(this.postService.update(postDto, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        postService.delete(id);
        return new ResponseEntity<String>("Post deleted successfully.", HttpStatus.OK);
    }
}
