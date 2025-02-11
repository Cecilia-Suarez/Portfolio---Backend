package com.ceciliasuarez.project.repository.translation;

import com.ceciliasuarez.project.model.translation.SkillTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISkillTranslationRepository extends JpaRepository<SkillTranslation, Long> {

    Optional<SkillTranslation> findBySkillIdAndLanguageCode(Long skillId, String languageCode);

    boolean existsByNameAndLanguageCode(String name, String languageCode);
}
