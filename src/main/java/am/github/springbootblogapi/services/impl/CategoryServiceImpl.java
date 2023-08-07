package am.github.springbootblogapi.services.impl;

import am.github.springbootblogapi.entities.Category;
import am.github.springbootblogapi.exceptions.ResourceNotFoundException;
import am.github.springbootblogapi.payloads.CategoryDTO;
import am.github.springbootblogapi.repositories.CategoryRepository;
import am.github.springbootblogapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper modelMapper,
                               CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    // .map(source, destination) // TODO: optimize
    private Category DTOToEntity(CategoryDTO categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
    private CategoryDTO entityToDTO(Category categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(this::entityToDTO) // .map(category -> entityToDTO(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDto) {
        Category category = DTOToEntity(categoryDto);
        Category categoryCreated = categoryRepository.save(category);

        return entityToDTO(categoryCreated);
    }

    @Override
    public CategoryDTO single(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(id)));

        return entityToDTO(category);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDto, int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(id)));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(id);

        Category categoryUpdated = categoryRepository.save(category);

        return entityToDTO(categoryUpdated);
    }

    @Override
    public void delete(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(id)));

        categoryRepository.delete(category);
    }
}
