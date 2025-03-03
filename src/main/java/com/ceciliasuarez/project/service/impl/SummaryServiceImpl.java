package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.Dto.SummaryDto;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Summary;
import com.ceciliasuarez.project.model.translation.SummaryTranslation;
import com.ceciliasuarez.project.repository.ISummaryRepository;
import com.ceciliasuarez.project.repository.translation.ISummaryTranslationRepository;
import com.ceciliasuarez.project.service.ISummaryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements ISummaryService {

    private static final Logger logger = Logger.getLogger(SummaryServiceImpl.class);
    private static final String DEFAULT_LANGUAGE = "es";

    @Autowired
    private ISummaryRepository summaryRepository;

    @Autowired
    private ISummaryTranslationRepository translationRepository;

    @Override
    public Summary getSummaryById(Long id) {
        logger.info("Searching summary by id...");
        return summaryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Error when searching the summary with the id " + id);
                    return new ResourceNotFoundException("The summary with the id does not exist.");
                });
    }

    @Override
    public List<Summary> getAllSummary() {
        logger.info("Fetching all summaries...");
        return summaryRepository.findAll();
    }

    @Override
    public Summary createSummary(Summary summary) {
        logger.info("Saving new summary...");

        for (SummaryTranslation translation : summary.getTranslations()) {
            translation.setSummary(summary);
        }

        summary = summaryRepository.save(summary);

        logger.info("Summary saved successfully!");
        return summary;
    }

    @Override
    public void updateSummary(Summary summary) {
        logger.info("Updating summary...");

        Summary existingSummary = summaryRepository.findById(summary.getId())
                .orElseThrow(() -> {
                    logger.info("Error updating summary.");
                    return new ResourceNotFoundException("The summary could not be updated because no summary with the given id exists.");
                });


        for (SummaryTranslation translation : summary.getTranslations()) {
            SummaryTranslation existingTranslation = translationRepository.findBySummaryIdAndLanguageCode(summary.getId(), translation.getLanguageCode()).orElseThrow();

            existingTranslation.setSummary(existingSummary);
            existingTranslation.setLanguageCode(translation.getLanguageCode());
            existingTranslation.setLocation(translation.getLocation());
            existingTranslation.setName(translation.getName());

            translationRepository.save(existingTranslation);
        }

        existingSummary.setYear(summary.getYear());
        existingSummary.setType(summary.getType());

        summaryRepository.save(existingSummary);
        logger.info("Summary updated successfully!");
    }

    @Override
    public void deleteSummary(Long id) {
        logger.info("Removing summary with id " + id);

        Summary summary = summaryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Summary removal failure.");
                    return new ResourceNotFoundException("There is no summary with the id " + id);
                });

        logger.info("Summary removed successfully!");
        summaryRepository.delete(summary);

    }

    @Override
    public SummaryTranslation addTranslation(Long summaryId, SummaryTranslation translation) {
        logger.info("Adding translation for summary id " + summaryId);

        Summary summary = summaryRepository.findById(summaryId)
                .orElseThrow(() -> {
                    logger.info("Error adding translation: summary not found.");
                    return new ResourceNotFoundException("Summary not found with id: " + summaryId);
                });

        translation.setSummary(summary);
        return translationRepository.save(translation);
    }

    @Override
    public SummaryDto getSummaryByLanguage(Long summaryId, String language) {
        logger.info("Fetching summary id " + summaryId + " with language " + language);

        Summary summary = summaryRepository.findById(summaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Summary not found with id: " + summaryId));

        SummaryTranslation translation = translationRepository
                .findBySummaryIdAndLanguageCode(summary.getId(), language)
                .or(() -> translationRepository.findBySummaryIdAndLanguageCode(summary.getId(), DEFAULT_LANGUAGE))
                .orElseThrow(() -> new ResourceNotFoundException("No translation found for summary id: " + summaryId));

        logger.info("Summary found successfully.");

        return new SummaryDto(summary.getId(), translation.getName(), translation.getLocation(), summary.getYear(), summary.getType());
    }
}
