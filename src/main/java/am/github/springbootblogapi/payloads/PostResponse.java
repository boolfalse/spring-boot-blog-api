package am.github.springbootblogapi.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Post Response fields")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDTO> content;
    private int page;
    private int perPage;
    private Long total;
    private int pages;
    private boolean last;
}
