package am.github.springbootblogapi.services;

import am.github.springbootblogapi.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAll();

    CategoryDTO create(CategoryDTO categoryDto);

    CategoryDTO single(int id);

    CategoryDTO update(CategoryDTO categoryDto, int id);

    void delete(int id);
}
