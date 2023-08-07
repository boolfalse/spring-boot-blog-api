package am.github.springbootblogapi.services;

import am.github.springbootblogapi.payloads.LoginDTO;

public interface AuthService {

    String login(LoginDTO loginDTO);

}
