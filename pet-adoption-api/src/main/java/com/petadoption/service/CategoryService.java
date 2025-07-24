package com.petadoption.service;

import com.petadoption.dto.CategoryDto;
import com.petadoption.exception.CategoryNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> getCategories();

    Optional<CategoryDto> getCategory(String id) throws CategoryNotFoundException;

    CategoryDto addCategory(CategoryDto categoryDto);

    Optional<CategoryDto> updateCategory(String id, CategoryDto updatedCategoryDto) throws CategoryNotFoundException;

    void deleteCategory(String id) throws CategoryNotFoundException;
}
