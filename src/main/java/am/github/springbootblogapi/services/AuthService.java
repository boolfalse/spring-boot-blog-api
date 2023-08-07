package am.github.springbootblogapi.services;

import am.github.springbootblogapi.payloads.LoginDTO;
import am.github.springbootblogapi.payloads.RegisterDTO;

public interface AuthService {

    String login(LoginDTO loginDto);

    String register(RegisterDTO registerDto);

}
