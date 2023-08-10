package am.github.springbootblogapi.controllers;

import am.github.springbootblogapi.payloads.BackResponse;
import am.github.springbootblogapi.payloads.CategoryDTO;
import am.github.springbootblogapi.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category CRUD Resource API")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get Categories", description = "Get all categories.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @Operation(summary = "Create Category", description = "Create category and get the created category as a result.")
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDto) {
        CategoryDTO createdCategoryDto = this.categoryService.create(categoryDto);

        return new ResponseEntity<CategoryDTO>(createdCategoryDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Category", description = "Get single category.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> single(@PathVariable(name = "id") int id) {
        CategoryDTO categoryDto = categoryService.single(id);

        return ResponseEntity.ok(categoryDto);
    }

    @Operation(summary = "Update Category", description = "Update category and get the updated category as a result.")
    @ApiResponse(responseCode = "202", description = "HTTP Status ACCEPTED")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @Valid @RequestBody CategoryDTO categoryDto,
            @PathVariable(name = "id") int id
    ) {
        CategoryDTO updatedCategoryDto = this.categoryService.update(categoryDto, id);

        return new ResponseEntity<CategoryDTO>(updatedCategoryDto, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete Category", description = "Delete category.")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BackResponse> delete(@PathVariable(name = "id") int id) {
        categoryService.delete(id);

        return new ResponseEntity<>(new BackResponse(true, "Category deleted successfully."), HttpStatus.OK);
    }
}
