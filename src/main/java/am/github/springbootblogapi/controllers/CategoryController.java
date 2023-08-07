package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.CategoryDTO;
import am.github.springbootblogapi.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @PreAuthorize("") // hasRole('ADMIN')
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDto) {
        CategoryDTO createdCategoryDto = this.categoryService.create(categoryDto);

        return new ResponseEntity<CategoryDTO>(createdCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> single(@PathVariable(name = "id") int id) {
        CategoryDTO categoryDto = categoryService.single(id);

        return ResponseEntity.ok(categoryDto);
    }

    @PreAuthorize("") // hasRole('ADMIN')
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @Valid @RequestBody CategoryDTO categoryDto,
            @PathVariable(name = "id") int id
    ) {
        CategoryDTO updatedCategoryDto = this.categoryService.update(categoryDto, id);

        return new ResponseEntity<CategoryDTO>(updatedCategoryDto, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("") // hasRole('ADMIN')
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        categoryService.delete(id);

        return new ResponseEntity<String>("Category deleted successfully.", HttpStatus.OK);
    }
}
