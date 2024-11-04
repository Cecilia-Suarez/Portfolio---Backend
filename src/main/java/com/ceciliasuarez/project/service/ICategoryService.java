package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategory();

    Category createCategory(Category category);

    void deleteCategory(Long id);
}
