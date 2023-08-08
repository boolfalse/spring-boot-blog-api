package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Schema(description = "Post DTO model info")
@Data
public class PostDTO {
    // primary field
    private Long id;

    // additional fields
    @Schema(description = "Post Title")
    @NotEmpty(message = "Title required!")
    @Size(min = 2, message = "Title should have at least 2 characters!")
    private String title;
    @Schema(description = "Post Description")
    @NotEmpty(message = "Description required!")
    @Size(min = 10, message = "Description should have at least 10 characters!")
    private String description;
    @Schema(description = "Post Content")
    @NotEmpty(message = "Content required!")
    @Size(min = 10, message = "Content should have at least 10 characters!")
    private String content;

    // relationships
    private Set<CommentDTO> comments; // post->comments one-to-many append
    @Schema(description = "Post Category")
    private int categoryId; // post->category
}
