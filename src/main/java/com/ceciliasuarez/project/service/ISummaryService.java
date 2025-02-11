package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.Dto.SummaryDto;
import com.ceciliasuarez.project.model.Summary;
import com.ceciliasuarez.project.model.translation.SummaryTranslation;

import java.util.List;

public interface ISummaryService {
    Summary getSummaryById(Long id);

    List<Summary> getAllSummary();

    Summary createSummary(Summary summary);

    void updateSummary(Summary summary);

    void deleteSummary(Long id);

    SummaryTranslation addTranslation(Long summaryId, SummaryTranslation translation);

    SummaryDto getSummaryByLanguage(Long summaryId, String language);
}
