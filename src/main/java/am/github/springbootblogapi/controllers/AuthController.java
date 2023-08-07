package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.AuthResponse;
import am.github.springbootblogapi.payloads.LoginDTO;
import am.github.springbootblogapi.payloads.RegisterDTO;
import am.github.springbootblogapi.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/sign-in"}) // multiple URIs accepted
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDto) {
        // TODO: validate fields
        String accessToken = authService.login(loginDto);

        return ResponseEntity.ok(new AuthResponse(accessToken));
    }

    @PostMapping(value = {"/register", "/sign-up"}) // multiple URIs accepted
    public ResponseEntity<String> login(@RequestBody RegisterDTO registerDto) {
        // TODO: validate fields
        String response = authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
