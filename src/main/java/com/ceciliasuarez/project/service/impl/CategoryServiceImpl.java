package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.Dto.CategoryDto;
import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.model.translation.CategoryTranslation;
import com.ceciliasuarez.project.repository.ICategoryRepository;
import com.ceciliasuarez.project.repository.translation.ICategoryTranslationRepository;
import com.ceciliasuarez.project.service.ICategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements ICategoryService {

    public static final String DEFAULT_LANGUAGE = "es";
    private final ICategoryRepository categoryRepository;
    private final ICategoryTranslationRepository translationRepository;

    @Autowired
    public CategoryServiceImpl(ICategoryRepository categoryRepository, ICategoryTranslationRepository translationRepository) {
        this.categoryRepository = categoryRepository;
        this.translationRepository = translationRepository;
    }

    private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto getCategoryByLanguage(Long categoryId, String language) {
        logger.info("Fetching category by id" + categoryId + " and language " + language);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        CategoryTranslation translation = translationRepository
                .findByCategoryIdAndLanguageCode(category.getId(), language)
                .or(() -> translationRepository.findByCategoryIdAndLanguageCode(category.getId(), DEFAULT_LANGUAGE))
                .orElseThrow(() -> new ResourceNotFoundException("No translation found for category id: " + categoryId));

        logger.info("Category found successfully.");

        return new CategoryDto(category.getId(), translation.getName(), translation.getLanguageCode());
    }

    @Override
    public Category createCategory(Category category) {
        logger.info("Saving new category...");

        if (translationExists(category.getTranslations())) {
            logger.warn("Error: A category with this name already exists for the given language.");
            throw new DuplicateResourceException("A category with this name already exists for the given language.");
        }

        for (CategoryTranslation translation : category.getTranslations()) {
            translation.setCategory(category);
        }

        category = categoryRepository.save(category);

        logger.info("Category saved successfully!");
        return category;
    }

    @Override
    public CategoryTranslation addTranslation(Long categoryId, CategoryTranslation translation) {
        logger.info("Adding translation for category id " + categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        translation.setCategory(category);
        return translationRepository.save(translation);
    }


    @Override
    public void updateCategory(Category category) {
        logger.info("Updating category...");

        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> {
                    logger.info("Error updating category.");
                    return new ResourceNotFoundException("The category could not be updated because no category with the given id exists.");
                });
        for (CategoryTranslation translation : category.getTranslations()) {
            CategoryTranslation existingTranslation = translationRepository.findByCategoryIdAndLanguageCode(category.getId(), translation.getLanguageCode()).orElseThrow();

            existingTranslation.setCategory(existingCategory);
            existingTranslation.setName(translation.getName());

            translationRepository.save(existingTranslation);
        }

        categoryRepository.save(existingCategory);
        logger.info("Category translation updated successfully!");

    }

    @Override
    public List<Category> getAllCategory() {
        logger.info("Category list:");
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
        logger.info("Removing category with id " + id);
        if (!categoryRepository.findById(id).isPresent()) {
            logger.info("Category removal failure.");
            throw new ResourceNotFoundException("There is no category with the id " + id);
        }

        categoryRepository.deleteById(id);
        logger.info("Category removed successfully!");
    }

    @Override
    public Category getCategoryById(Long id) {
        logger.info("Searching category by id...");
        return categoryRepository.findById(id).orElseThrow(() -> {
            logger.info("Error when searching the category with the id " + id);
            return new ResourceNotFoundException("The category with the id does not exist.");
        });
    }

    private boolean translationExists(List<CategoryTranslation> translations) {
        for (CategoryTranslation translation : translations) {
            if (translationRepository.existsByNameIgnoreCase(translation.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}

