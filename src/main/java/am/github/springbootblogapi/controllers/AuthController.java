package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.LoginDTO;
import am.github.springbootblogapi.services.AuthService;
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
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String response = authService.login(loginDTO);

        return ResponseEntity.ok(response);
    }
}
