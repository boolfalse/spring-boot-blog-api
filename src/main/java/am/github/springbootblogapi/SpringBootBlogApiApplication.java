package am.github.springbootblogapi;

import am.github.springbootblogapi.config.AppConstants;
import am.github.springbootblogapi.entities.Role;
import am.github.springbootblogapi.entities.User;
import am.github.springbootblogapi.repositories.RoleRepository;
import am.github.springbootblogapi.repositories.UserRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@OpenAPIDefinition(info = @Info(
		title = "Spring Boot Blog API",
		description = "Spring Boot Blog API documentation",
		version = "v1.0.0",
		contact = @Contact(
				name = AppConstants.AUTHOR_NICKNAME,
				email = AppConstants.AUTHOR_EMAIL,
				url = AppConstants.AUTHOR_URL
		)
), externalDocs = @ExternalDocumentation(
		description = "Postman Collection",
		url = AppConstants.POSTMAN_COLLECTION_PUBLISHED
))
@EnableWebMvc
@SpringBootApplication
public class SpringBootBlogApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper ModelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApiApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Value("${app.admin-email}")
	private String adminEmail;
	@Value("${app.admin-username}")
	private String adminUsername;
	@Value("${app.admin-password}")
	private String adminPassword;

	@Override
	public void run(String... args) throws Exception {

		if (!roleRepository.existsByAlias("ROLE_ADMIN")) {
			Role adminRole = new Role();
			adminRole.setAlias("ROLE_ADMIN");
			adminRole.setName("Admin");
			roleRepository.save(adminRole);
		}
		if (!roleRepository.existsByAlias("ROLE_CLIENT")) {
			Role clientRole = new Role();
			clientRole.setAlias("ROLE_CLIENT");
			clientRole.setName("Client");
			roleRepository.save(clientRole);
		}

		if (!userRepository.existsByEmail(this.adminEmail)) {
			User user = new User();
			user.setEmail(this.adminEmail);
			user.setUsername(this.adminUsername);
			user.setPassword(passwordEncoder.encode(this.adminPassword));
			user.setName("Admin");
			userRepository.save(user);

			Role createdRole = roleRepository.findByAlias(this.adminEmail)
					.orElseThrow(() -> new RuntimeException("Role not found!"));
			// Fetch from the database to avoid following error:
			// detached entity passed to persist entities Role
			User createdUser = userRepository.findByEmail(this.adminEmail)
					.orElseThrow(() -> new RuntimeException("User role not found!"));

			createdUser.getRoles().add(createdRole);
			userRepository.save(createdUser);
		}
	}
}
