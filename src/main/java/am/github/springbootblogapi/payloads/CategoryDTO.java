package am.github.springbootblogapi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    // primary field
    private int id;

    // additional fields
    @NotEmpty(message = "Name required!")
    @Size(min = 2, message = "Name should have at least 2 characters!")
    private String name;
    private String description;

    // relationships
    private List<PostDTO> posts; // category->posts one-to-many append
}
