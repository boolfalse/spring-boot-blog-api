package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Schema(description = "Category DTO model info")
@Data
public class CategoryDTO {
    // primary field
    private int id;

    // additional fields
    @Schema(description = "Category Name")
    @NotEmpty(message = "Name required!")
    @Size(min = 2, message = "Name should have at least 2 characters!")
    private String name;
    @Schema(description = "Category Description")
    private String description;

    // relationships
    private List<PostDTO> posts; // category->posts one-to-many append
}
