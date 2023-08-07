package am.github.springbootblogapi.services.impl;

import am.github.springbootblogapi.entities.Category;
import am.github.springbootblogapi.entities.Post;
import am.github.springbootblogapi.exceptions.ResourceNotFoundException;
import am.github.springbootblogapi.payloads.PostDTO;
import am.github.springbootblogapi.payloads.PostResponse;
import am.github.springbootblogapi.repositories.CategoryRepository;
import am.github.springbootblogapi.repositories.PostRepository;
import am.github.springbootblogapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(ModelMapper modelMapper,
                           PostRepository postRepository,
                           CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    // .map(source, destination) // TODO: optimize
    private Post DTOToEntity(PostDTO postDto) {
        return modelMapper.map(postDto, Post.class);
    }
    private PostDTO entityToDTO(Post postEntity) {
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostResponse getAll(int page, int per_page, String sort_by, String order_by) {
        Sort sortOrder = Objects.equals(order_by, "asc")
                ? Sort.by(sort_by).ascending()
                : Sort.by(sort_by).descending();
        Pageable pageable = PageRequest.of(
                page - 1, // pages starts from 0
                per_page,
                sortOrder
        );
        Page<Post> pageablePosts = postRepository.findAll(pageable);
        List<Post> posts = pageablePosts.getContent();

        List<PostDTO> content = posts.stream()
                .map(this::entityToDTO) // .map(post -> entityToDTO(post))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPage(page);
        postResponse.setPerPage(per_page);
        postResponse.setTotal(pageablePosts.getTotalElements());
        postResponse.setPages(pageablePosts.getTotalPages());
        postResponse.setLast(pageablePosts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO create(PostDTO postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(postDto.getCategoryId())));

        Post post = DTOToEntity(postDto);
        post.setCategory(category);
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
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(postDto.getCategoryId())));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post postUpdated = postRepository.save(post);

        return entityToDTO(postUpdated);
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getCategoryPosts(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(categoryId)));

        List<Post> posts = postRepository.findPostsByCategoryId(categoryId);

        return posts.stream()
                .map(post -> entityToDTO(post))
                .collect(Collectors.toList());
    }
}
