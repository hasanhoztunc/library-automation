package com.hasanoztunc.library_automation.features.category;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;

import java.util.List;

public interface CategoryService {

    GenericResponse<CategoryDTO> createCategory(CategoryDTO request);

    GenericResponse<List<CategoryDTO>> getAllCategories();

    GenericResponse<CategoryDTO> getCategoryById(Long id);

    GenericResponse<List<CategoryDTO>> searchCategoriesByName(String name);

    GenericResponse<CategoryDTO> updateCategory(Long id, CategoryDTO request);

    GenericResponse<CategoryDTO> deleteCategory(Long id);
}