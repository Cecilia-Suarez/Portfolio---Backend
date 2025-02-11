package com.ceciliasuarez.project.repository.translation;

import com.ceciliasuarez.project.model.translation.CategoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryTranslationRepository extends JpaRepository<CategoryTranslation, Long> {

    Optional<CategoryTranslation> findByCategoryIdAndLanguageCode(Long categoryId, String language);

    boolean existsByNameIgnoreCase(String name);
}
