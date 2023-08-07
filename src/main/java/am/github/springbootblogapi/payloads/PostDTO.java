package am.github.springbootblogapi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    // primary field
    private Long id;

    // additional fields
    @NotEmpty(message = "Title required!")
    @Size(min = 2, message = "Title should have at least 2 characters!")
    private String title;
    @NotEmpty(message = "Description required!")
    @Size(min = 10, message = "Description should have at least 10 characters!")
    private String description;
    @NotEmpty(message = "Content required!")
    @Size(min = 10, message = "Content should have at least 10 characters!")
    private String content;

    // relationships
    private Set<CommentDTO> comments; // post->comments one-to-many append
    private int categoryId; // post->category
}
