package am.github.springbootblogapi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDTO {
    // primary field
    private Long id;

    // additional fields
    @NotEmpty(message = "Name required!")
    private String name;
    @NotEmpty(message = "Email required!")
    @Email
    private String email;
    @NotEmpty(message = "Comment body required!")
    private String body;
}
