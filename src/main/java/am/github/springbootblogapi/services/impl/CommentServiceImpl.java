package am.github.springbootblogapi.services.impl;

import am.github.springbootblogapi.entities.Comment;
import am.github.springbootblogapi.entities.Post;
import am.github.springbootblogapi.exceptions.ResourceNotFoundException;
import am.github.springbootblogapi.payloads.CommentDTO;
import am.github.springbootblogapi.repositories.CommentRepository;
import am.github.springbootblogapi.repositories.PostRepository;
import am.github.springbootblogapi.services.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    private Comment DTOToEntity(CommentDTO commentDto) {
        Comment commentEntity = new Comment();
        commentEntity.setId(commentDto.getId());
        commentEntity.setName(commentDto.getName());
        commentEntity.setEmail(commentDto.getEmail());
        commentEntity.setBody(commentDto.getBody());

        return commentEntity;
    }
    private CommentDTO entityToDTO(Comment commentEntity) {
        CommentDTO commentDto = new CommentDTO();
        commentDto.setId(commentEntity.getId());
        commentDto.setName(commentEntity.getName());
        commentDto.setEmail(commentEntity.getEmail());
        commentDto.setBody(commentEntity.getBody());

        return commentDto;
    }

    @Override
    public List<CommentDTO> getAll(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);

        return comments.stream()
                .map(this::entityToDTO) // .map(comment -> entityToDTO(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO create(Long postId, CommentDTO commentDto) {
        Comment comment = DTOToEntity(commentDto);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));
        comment.setPost(post);
        Comment commentCreated = commentRepository.save(comment);

        return entityToDTO(commentCreated);
    }

    @Override
    public CommentDTO single(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(id)));

        return entityToDTO(comment);
    }

    @Override
    public CommentDTO update(CommentDTO commentDto, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(id)));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment commentUpdated = commentRepository.save(comment);

        return entityToDTO(commentUpdated);
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(id)));

        commentRepository.delete(comment);
    }
}
