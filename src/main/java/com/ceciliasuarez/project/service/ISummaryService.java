package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.model.Summary;

import java.util.List;

public interface ISummaryService {
    Summary getSummaryById(Long id);

    List<Summary> getAllSummary(); //Ordenar por año

    Summary createSummary(Summary summary);

    Summary updateSummary(Long id, Summary summary);

    void deleteSummary(Long id);
}
