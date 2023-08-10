package am.github.springbootblogapi.services.impl;

import am.github.springbootblogapi.entities.Role;
import am.github.springbootblogapi.entities.User;
import am.github.springbootblogapi.exceptions.ApiException;
import am.github.springbootblogapi.payloads.LoginDTO;
import am.github.springbootblogapi.payloads.RegisterDTO;
import am.github.springbootblogapi.repositories.RoleRepository;
import am.github.springbootblogapi.repositories.UserRepository;
import am.github.springbootblogapi.services.AuthService;
import am.github.springbootblogapi.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDTO loginDto) {
        Authentication authInstance = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authInstance);

        return jwtTokenProvider.generateToken(authInstance);
    }

    @Override
    public String register(RegisterDTO registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email is already exists!");
        }

        Role role = roleRepository.findByAlias("ROLE_CLIENT")
                .orElseThrow(() -> new RuntimeException("Client Role not found!"));

        User user = new User();

        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setName(registerDto.getName());

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "Registered successfully.";
    }

}
