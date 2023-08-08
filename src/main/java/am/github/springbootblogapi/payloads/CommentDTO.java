package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(description = "Comment DTO model info")
@Data
public class CommentDTO {
    // primary field
    private Long id;

    // additional fields
    @Schema(description = "Author Name")
    @NotEmpty(message = "Name required!")
    private String name;
    @Schema(description = "Author Email")
    @NotEmpty(message = "Email required!")
    @Email
    private String email;
    @Schema(description = "Actual Comment")
    @NotEmpty(message = "Comment body required!")
    private String body;
}
