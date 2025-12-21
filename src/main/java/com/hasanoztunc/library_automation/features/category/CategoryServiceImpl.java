package com.hasanoztunc.library_automation.features.category;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            ModelMapper modelMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GenericResponse<CategoryDTO> createCategory(CategoryDTO request) {
        var category = modelMapper.map(request, Category.class);

        var savedCategory = categoryRepository.save(category);
        var categoryDto = modelMapper.map(savedCategory, CategoryDTO.class);

        return GenericResponse.success(categoryDto);
    }

    @Override
    public GenericResponse<List<CategoryDTO>> getAllCategories() {
        var categories = categoryRepository.findAll();
        var categoriesDto = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        return GenericResponse.success(categoriesDto);
    }

    @Override
    public GenericResponse<CategoryDTO> getCategoryById(Long id) {
        var category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            return GenericResponse.fail("Category not found");
        }

        var categoryDto = modelMapper.map(category, CategoryDTO.class);
        return GenericResponse.success(categoryDto);
    }

    @Override
    public GenericResponse<List<CategoryDTO>> searchCategoriesByName(String name) {
        var categories = categoryRepository.findByNameContainingIgnoreCase(name);

        var categoriesDto = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        return GenericResponse.success(categoriesDto);
    }

    @Transactional
    @Override
    public GenericResponse<CategoryDTO> updateCategory(Long id, CategoryDTO request) {
        var categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isEmpty()) {
            return GenericResponse.fail("Category not found");
        }

        var category = categoryOpt.get();
        category.setName(request.getName());

        var updatedCategory = categoryRepository.save(category);
        var categoryDto = modelMapper.map(updatedCategory, CategoryDTO.class);

        return GenericResponse.success(categoryDto);
    }

    @Transactional
    @Override
    public GenericResponse<CategoryDTO> deleteCategory(Long id) {
        var categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isEmpty()) {
            return GenericResponse.fail("Category not found");
        }

        var category = categoryOpt.get();
        categoryRepository.delete(category);

        var categoryDto = modelMapper.map(category, CategoryDTO.class);
        return GenericResponse.success(categoryDto);
    }
}