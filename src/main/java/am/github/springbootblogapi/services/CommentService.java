package am.github.springbootblogapi.services;

import am.github.springbootblogapi.payloads.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getAll(Long postId);

    CommentDTO create(Long postId, CommentDTO commentDto);

    CommentDTO single(Long id);

    CommentDTO update(CommentDTO commentDto, Long id);

    void delete(Long id);
}
