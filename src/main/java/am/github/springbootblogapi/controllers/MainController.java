package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.ErrorResponse;
import am.github.springbootblogapi.utils.Helper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Main URL")
@Controller
public class MainController {

    @Operation(summary = "Main API-endpoint", description = "This is a main API-endpoint.")
    @ApiResponse(responseCode = "403", description = "HTTP Status FORBIDDEN")
    @GetMapping(value = {"/api"}) // , "/api/"
    @ResponseBody
    public ResponseEntity<ErrorResponse> mainUri(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String host = scheme + "://" + serverName + ":" + port;

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "This is a main API-endpoint. Try " + host + "/swagger-ui/index.html to see the API docs.",
                Helper.getNowDateFormatted()
        ), HttpStatus.FORBIDDEN);
    }

}
