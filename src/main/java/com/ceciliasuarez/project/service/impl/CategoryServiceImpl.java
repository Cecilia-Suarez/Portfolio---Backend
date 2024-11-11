package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.repository.ICategoryRepository;
import com.ceciliasuarez.project.service.ICategoryService;
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
        logger.info("Categories list:");
        return categoryRepository.findAll();

    }

    @Override
    public Category createCategory(Category category) {
        logger.info("Saving new category...");
        List<Category> listCategories = getAllCategory();
        for (Category existingCategory: listCategories) {
            if (existingCategory.getName().equals(category.getName())){
                logger.info("Error when saving new category.");
                throw new DuplicateResourceException("There is already a category with that name.");
            }
        }
        logger.info("Category saved successfully!");
        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(Long id) {
        logger.info("Removing category with id " + id);
        if (!categoryRepository.findById(id).isPresent()) {
            logger.info("Category removal failure.");
            throw new ResourceNotFoundException("There is no category with the id " + id);
        }
        logger.info("Category removed successfully!");
        categoryRepository.deleteById(id);
    }

}
