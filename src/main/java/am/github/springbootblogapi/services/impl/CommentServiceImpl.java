package am.github.springbootblogapi.services.impl;

import am.github.springbootblogapi.entities.Comment;
import am.github.springbootblogapi.entities.Post;
import am.github.springbootblogapi.exceptions.ResourceNotFoundException;
import am.github.springbootblogapi.payloads.CommentDTO;
import am.github.springbootblogapi.repositories.CommentRepository;
import am.github.springbootblogapi.repositories.PostRepository;
import am.github.springbootblogapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private ModelMapper modelMapper;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(ModelMapper modelMapper,
                              CommentRepository commentRepository,
                              PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // .map(source, destination)
    private Comment DTOToEntity(CommentDTO commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
    private CommentDTO entityToDTO(Comment commentEntity) {
        return modelMapper.map(commentEntity, CommentDTO.class);
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
