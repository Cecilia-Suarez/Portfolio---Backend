package com.ceciliasuarez.project.repository.translation;

import com.ceciliasuarez.project.model.translation.ProjectTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProjectTranslationRepository extends JpaRepository<ProjectTranslation, Long> {

    Optional<ProjectTranslation> findByProjectIdAndLanguageCode(Long projectId, String languageCode);

    boolean existsByNameIgnoreCase(String name);
}
