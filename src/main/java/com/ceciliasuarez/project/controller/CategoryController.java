package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @PostMapping("/new")
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}
