package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<PostDTO> getAll(
            @RequestParam(value = "page", defaultValue = "1", required = false) String page,
            @RequestParam(value = "per_page", defaultValue = "2", required = false) String per_page
    ) {
        int pageNumber;
        int perPageNumber;
        // TODO: separate validation part
        try {
            pageNumber = Integer.parseInt(page);
            perPageNumber = Integer.parseInt(per_page);
            if (pageNumber < 1 || pageNumber > 1000 || perPageNumber < 1 || perPageNumber > 100) {
                pageNumber = 1;
                perPageNumber = 10;
            }
        } catch (NumberFormatException e) {
            pageNumber = 1;
            perPageNumber = 10;
        }

        return postService.getAll(pageNumber, perPageNumber);
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