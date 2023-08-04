package am.github.springbootblogapi.services;

import am.github.springbootblogapi.payloads.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAll();

    PostDTO create(PostDTO postDto);

    PostDTO single(Long id);

    PostDTO update(PostDTO postDto, Long id);

    void delete(Long id);
}
