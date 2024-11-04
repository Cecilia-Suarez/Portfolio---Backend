package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.model.Summary;
import com.ceciliasuarez.project.service.ISummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/summaries")
public class SummaryController {
    private final ISummaryService summaryService;

    @Autowired
    public SummaryController(ISummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/all")
    public List<Summary> getAllSummary(){
        return summaryService.getAllSummary();
    }

    @GetMapping("/{id}")
    public Optional<Summary> getSummaryById(@PathVariable Long id){
        return summaryService.getSummaryById(id);
    }

    @PostMapping("/new")
    public Summary createSummary(@RequestBody Summary summary){
        return summaryService.createSummary(summary);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSummary(@PathVariable Long id){
        summaryService.deleteSummary(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public Summary updateSummary(@PathVariable Long id, Summary summary){
        return summaryService.updateSummary(id, summary);
    }
}
