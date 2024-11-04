package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.model.Summary;

import java.util.List;
import java.util.Optional;

public interface ISummaryService {
    Optional<Summary> getSummaryById(Long id);

    List<Summary> getAllSummary(); //Ordenar por año

    Summary createSummary(Summary summary);

    void updateSummary(Summary summary);

    void deleteSummary(Long id);
}
