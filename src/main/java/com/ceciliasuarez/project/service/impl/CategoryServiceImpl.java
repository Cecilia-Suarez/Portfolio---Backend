package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.repository.ICategoryRepository;
import com.ceciliasuarez.project.service.ICategoryService;
import org.apache.coyote.BadRequestException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        logger.info("Saving new category...");
        List<Category> listCategories = getAllCategory();
        for (Category existingcategory: listCategories) {
            if (existingcategory.getName().equals(category.getName())){
                logger.info("The new category could not be saved because a category with that name already exists.");
                throw new DuplicateResourceException("A category with this name already exists.");
            }
        }
        logger.info("¡Category saved successfully!");
        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
