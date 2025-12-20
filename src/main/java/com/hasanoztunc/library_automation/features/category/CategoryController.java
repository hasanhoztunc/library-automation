package com.hasanoztunc.library_automation.features.category;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<CategoryDTO>> createCategory(@RequestBody CategoryDTO request) {
        var createdCategory = categoryService.createCategory(request);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/")
    public ResponseEntity<GenericResponse<List<CategoryDTO>>> getAllCategories() {
        var categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        var category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse<List<CategoryDTO>>> searchCategoriesByName(@RequestParam String name) {
        var categories = categoryService.searchCategoriesByName(name);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<CategoryDTO>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO request
    ) {
        var updatedCategory = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<CategoryDTO>> deleteCategory(@PathVariable Long id) {
        var response = categoryService.deleteCategory(id);
        return ResponseEntity.ok(response);
    }
}