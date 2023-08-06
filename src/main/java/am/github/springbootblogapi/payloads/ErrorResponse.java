package am.github.springbootblogapi.payloads;

public class ErrorResponse {
    private int code;
    private String message;
    private String date;

    public ErrorResponse(int code, String message, String date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public String getDate() {
        return date;
    }
}