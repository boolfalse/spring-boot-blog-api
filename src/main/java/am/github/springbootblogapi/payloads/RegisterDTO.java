package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Register DTO info")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String email;
    private String username;
    private String password;
    private String name;
}
