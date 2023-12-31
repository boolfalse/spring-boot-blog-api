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
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(ModelMapper modelMapper,
                              CommentRepository commentRepository,
                              PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<CommentDTO> getAll(Long postId) {
        // Post existingPost
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);

        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO create(Long postId, CommentDTO commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));
        comment.setPost(post);
        Comment commentCreated = commentRepository.save(comment);

        return modelMapper.map(commentCreated, CommentDTO.class);
    }

    @Override
    public CommentDTO single(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(id)));

        return modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public CommentDTO update(CommentDTO commentDto, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(id)));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment commentUpdated = commentRepository.save(comment);

        return modelMapper.map(commentUpdated, CommentDTO.class);
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(id)));

        commentRepository.delete(comment);
    }
}
