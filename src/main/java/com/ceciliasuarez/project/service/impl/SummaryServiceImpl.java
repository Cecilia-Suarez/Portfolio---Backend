package com.ceciliasuarez.project.service.impl;
import com.ceciliasuarez.project.model.Summary;
import com.ceciliasuarez.project.repository.ISummaryRepository;
import com.ceciliasuarez.project.service.ISummaryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SummaryServiceImpl implements ISummaryService {

    private ISummaryRepository summaryRepository;

    @Override
    public Optional<Summary> getSummaryById(Long id) {
        return summaryRepository.findById(id);
    }

    @Override
    public List<Summary> getAllSummary() {
        return summaryRepository.findAll();
    }

    @Override
    public Summary createSummary(Summary summary) {
        return summaryRepository.save(summary);
    }

    @Override
    public Summary updateSummary(Long id, Summary summary) {
        return summaryRepository.save(summary);
    }

    @Override
    public void deleteSummary(Long id) {
        summaryRepository.deleteById(id);
    }
}
