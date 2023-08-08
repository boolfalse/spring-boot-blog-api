package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.AuthResponse;
import am.github.springbootblogapi.payloads.LoginDTO;
import am.github.springbootblogapi.payloads.BackResponse;
import am.github.springbootblogapi.payloads.RegisterDTO;
import am.github.springbootblogapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login", description = "Sign-in user and get credentials.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @PostMapping(value = {"/login", "/sign-in"}) // multiple URIs accepted
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDto) {
        // TODO: validate fields
        String accessToken = authService.login(loginDto);

        return ResponseEntity.ok(new AuthResponse(accessToken));
    }

    @Operation(summary = "Register", description = "Sign-up user.")
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping(value = {"/register", "/sign-up"}) // multiple URIs accepted
    public ResponseEntity<BackResponse> login(@RequestBody RegisterDTO registerDto) {
        // TODO: validate fields
        String response = authService.register(registerDto);

        return new ResponseEntity<>(new BackResponse(true, response), HttpStatus.CREATED);
    }
}
