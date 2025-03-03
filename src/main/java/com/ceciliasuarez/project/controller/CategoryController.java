package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.Dto.CategoryDto;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.model.translation.CategoryTranslation;
import com.ceciliasuarez.project.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/new")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        category = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            category.setId(id);
            categoryService.updateCategory(category);
            return ResponseEntity.ok("Category updated successfully!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/translations")
    public CategoryTranslation addTranslation(@PathVariable Long id, @RequestBody CategoryTranslation translation) {
        return categoryService.addTranslation(id, translation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/detail/{id}/translations")
    public ResponseEntity<CategoryDto> getCategoryByLanguage(@PathVariable Long id, @RequestHeader(value = "Accept-Language", defaultValue = "es") String language) {
        CategoryDto categoryDto = categoryService.getCategoryByLanguage(id, language);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/all")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }
}
