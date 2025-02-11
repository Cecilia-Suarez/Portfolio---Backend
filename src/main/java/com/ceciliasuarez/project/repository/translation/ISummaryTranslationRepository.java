package com.ceciliasuarez.project.repository.translation;

import com.ceciliasuarez.project.model.translation.SummaryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISummaryTranslationRepository extends JpaRepository<SummaryTranslation, Long> {

    Optional<SummaryTranslation> findBySummaryIdAndLanguageCode(Long summaryId, String languageCode);
}
