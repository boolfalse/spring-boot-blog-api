package am.github.springbootblogapi.services.impl;

import am.github.springbootblogapi.entities.Post;
import am.github.springbootblogapi.exceptions.ResourceNotFoundException;
import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.repositories.PostRepository;
import am.github.springbootblogapi.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private Post DTOToEntity(PostDTO postDto) {
        Post postEntity = new Post();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDescription(postDto.getDescription());
        postEntity.setContent(postDto.getContent());

        return postEntity;
    }
    private PostDTO entityToDTO(Post postEntity) {
        PostDTO postDto = new PostDTO();
        postDto.setId(postEntity.getId());
        postDto.setTitle(postEntity.getTitle());
        postDto.setDescription(postEntity.getDescription());
        postDto.setContent(postEntity.getContent());

        return postDto;
    }

    @Override
    public List<PostDTO> getAll(int page, int per_page) {
        Pageable pageable = PageRequest.of(page - 1, per_page); // pages starts from 0
        Page<Post> pageablePosts = postRepository.findAll(pageable);
        List<Post> posts = pageablePosts.getContent();

        return posts.stream()
                .map(this::entityToDTO) // .map(post -> entityToDTO(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO create(PostDTO postDto) {
        Post post = DTOToEntity(postDto);
        Post postCreated = postRepository.save(post);

        return entityToDTO(postCreated);
    }

    @Override
    public PostDTO single(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        return entityToDTO(post);
    }

    @Override
    public PostDTO update(PostDTO postDto, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post postUpdated = postRepository.save(post);

        return entityToDTO(postUpdated);
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        postRepository.delete(post);
    }
}
