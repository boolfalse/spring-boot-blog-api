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
    public List<PostDTO> getAll() {
        return postService.getAll();
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
