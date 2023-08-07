package am.github.springbootblogapi.services;

import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse getAll(int page, int per_page, String sort_by, String order_by);

    PostDTO create(PostDTO postDto);

    PostDTO single(Long id);

    PostDTO update(PostDTO postDto, Long id);

    void delete(Long id);

    List<PostDTO> getCategoryPosts(int categoryId);
}
