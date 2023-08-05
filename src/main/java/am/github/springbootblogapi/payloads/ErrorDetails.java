package am.github.springbootblogapi.payloads;

import lombok.Getter;

public class ErrorDetails {
    @Getter
    private String date;
    private String message;
    private String description;
    private int code;

    public ErrorDetails(String date, String message, String description, int code) {
        this.date = date;
        this.message = message;
        this.description = description;
        this.code = code;
    }

    public String getDate() {
        return date;
    }
    public String getMessage() {
        return message;
    }
    public String getDescription() {
        return description;
    }
    public int getCode() {
        return code;
    }
}
