package am.github.springbootblogapi.payloads;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDTO> comments; // post->comments one-to-many append
}
