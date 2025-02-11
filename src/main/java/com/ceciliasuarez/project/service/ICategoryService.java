package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.Dto.CategoryDto;
import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.model.translation.CategoryTranslation;

import java.util.List;


public interface ICategoryService {
    List<Category> getAllCategory();

    Category createCategory(Category category);

    void deleteCategory(Long id);

    Category getCategoryById(Long id);

    CategoryDto getCategoryByLanguage(Long categoryId, String language);

    CategoryTranslation addTranslation(Long categoryId, CategoryTranslation translation);

    void updateCategory(Category category);
}
