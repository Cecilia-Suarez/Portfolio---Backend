package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.Dto.SummaryDto;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Summary;
import com.ceciliasuarez.project.model.translation.SummaryTranslation;
import com.ceciliasuarez.project.service.impl.SummaryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/summaries")
@CrossOrigin(origins = "*")
public class SummaryController {

    private final SummaryServiceImpl summaryService;

    @Autowired
    public SummaryController(SummaryServiceImpl summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping("/new")
    public ResponseEntity<Summary> createSummary(@Valid @RequestBody Summary summary) {
        summary = summaryService.createSummary(summary);
        return ResponseEntity.status(HttpStatus.CREATED).body(summary);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Summary> getSummaryById(@PathVariable Long id) {
        Summary summary = summaryService.getSummaryById(id);
        return ResponseEntity.ok(summary);
    }

    @PostMapping("/{id}/translations")
    public SummaryTranslation addTranslation(@PathVariable Long id, @RequestBody SummaryTranslation translation) {
        return summaryService.addTranslation(id, translation);
    }

    @GetMapping("/all")
    public List<Summary> getAllSummary() {
        return summaryService.getAllSummary();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSummary(@PathVariable Long id, @RequestBody Summary summary) {
        try {
            summary.setId(id);
            summaryService.updateSummary(summary);
            return ResponseEntity.ok("Summary updated successfully!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSummary(@PathVariable Long id) {
        summaryService.deleteSummary(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/detail/{id}/translations")
    public ResponseEntity<SummaryDto> getSummaryByLanguage(@PathVariable Long id, @RequestHeader(value = "Accept-Language", defaultValue = "es") String language) {
        SummaryDto summaryDto = summaryService.getSummaryByLanguage(id, language);
        return ResponseEntity.ok(summaryDto);
    }
}

