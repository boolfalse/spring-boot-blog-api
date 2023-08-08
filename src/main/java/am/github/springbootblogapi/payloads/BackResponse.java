package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Back Response fields")
@Getter
@Setter
@NoArgsConstructor
public class BackResponse {
    private Boolean success = true;
    private String message;

    public BackResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
