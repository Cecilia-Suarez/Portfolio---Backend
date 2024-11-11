package com.ceciliasuarez.project.service.impl;
import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Summary;
import com.ceciliasuarez.project.repository.ISummaryRepository;
import com.ceciliasuarez.project.service.ISummaryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SummaryServiceImpl implements ISummaryService {

    private static final Logger logger = Logger.getLogger(SummaryServiceImpl.class);

    @Autowired
    private ISummaryRepository summaryRepository;

    @Override
    public Optional<Summary> getSummaryById(Long id) {
        logger.info("Searching summary by id...");
        Optional<Summary> summary = summaryRepository.findById(id);
        if (!summary.isPresent()){
            logger.info("Error when searching the summary with the id " + id);
            throw new ResourceNotFoundException("The summary with the id does not exist.");
        }
        logger.info("Summary found successfully!");
        return summary;
    }

    @Override
    public List<Summary> getAllSummary() {
        logger.info("Summaries list:");
        return summaryRepository.findAll();
    }

    @Override
    public Summary createSummary(Summary summary) {
        logger.info("Saving new summary...");
        List<Summary> listSummaries = getAllSummary();
        for (Summary existingSummary: listSummaries) {
            if (existingSummary.getName().equals(summary.getName())
                    && existingSummary.getLocation().equals(summary.getLocation())
                    && existingSummary.getYear() == summary.getYear()){
                logger.info("Error when saving new category.");
                throw new DuplicateResourceException("A summary with this name, location and year already exists.");
            }
        }
        logger.info("Summary saved successfully!");
        return summaryRepository.save(summary);
    }

    @Override
    public void updateSummary(Summary summary) {
        logger.info("Updating summary...");
        if (getSummaryById(summary.getId()) == null){
            logger.info("Error updating summary.");
            throw new ResourceNotFoundException("The summary could not be updated, because there is no summary with the given id.");
        }
        logger.info("Summary updated successfully!");
        summaryRepository.save(summary);
    }

    @Override
    public void deleteSummary(Long id) {
        logger.info("Removing summary with id " + id);
        if (!summaryRepository.findById(id).isPresent()) {
            logger.info("Summary removal failure.");
            throw new ResourceNotFoundException("There is no summary with the id " + id);
        }
        logger.info("Summary removed successfully!");
        summaryRepository.deleteById(id);
    }
}
