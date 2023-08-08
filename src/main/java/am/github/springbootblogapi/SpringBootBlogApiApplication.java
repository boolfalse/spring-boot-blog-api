package am.github.springbootblogapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@OpenAPIDefinition(info = @Info(
		title = "Spring Boot Blog API",
		description = "Spring Boot Blog API documentation",
		version = "v1.0.0",
		contact = @Contact(
				name = "BoolFalse",
				email = "email@boolfalse.com",
				url = "https://boolfalse.com"
		)
), externalDocs = @ExternalDocumentation(
		description = "Postman Collection",
		url = "https://documenter.getpostman.com/view/1747137/2s9XxyRDsF"
))
@EnableWebMvc
@SpringBootApplication
public class SpringBootBlogApiApplication {

	@Bean
	public ModelMapper ModelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApiApplication.class, args);
	}

}
