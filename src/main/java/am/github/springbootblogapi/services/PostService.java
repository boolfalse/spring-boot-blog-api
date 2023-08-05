package am.github.springbootblogapi.services;

import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.payloads.PostResponse;

public interface PostService {

    PostResponse getAll(int page, int per_page);

    PostDTO create(PostDTO postDto);

    PostDTO single(Long id);

    PostDTO update(PostDTO postDto, Long id);

    void delete(Long id);
}
