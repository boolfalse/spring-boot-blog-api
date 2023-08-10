package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Error Response fields")
public class ErrorResponse {
    private final int code;
    private final String message;
    private final String date;

    public ErrorResponse(int code, String message, String date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }
}
