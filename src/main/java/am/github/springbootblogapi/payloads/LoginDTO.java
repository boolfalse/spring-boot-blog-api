package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Login DTO info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotEmpty(message = "Username/Email required!")
    private String usernameOrEmail;
    @NotEmpty(message = "Password required!")
    @Size(min = 6, message = "Password should have at least 6 characters!")
    private String password;
}
