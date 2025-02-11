package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.Dto.ProjectDto;
import com.ceciliasuarez.project.model.Project;
import com.ceciliasuarez.project.model.translation.ProjectTranslation;
import com.ceciliasuarez.project.service.IProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    private final IProjectService projectService;

    @Autowired
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public List<Project> getAllProject() {
        return projectService.getAllProject();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PostMapping("/new")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        project = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PostMapping("/{id}/translations")
    public ProjectTranslation addTranslation(@PathVariable Long id, @RequestBody ProjectTranslation translation) {
        return projectService.addTranslation(id, translation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestHeader(value = "Accept-Language", defaultValue = "en") String language, @RequestBody Project project) {
        projectService.updateProject(id, project);
        return ResponseEntity.ok("Project and translation updated successfully!");
    }

    @GetMapping("/detail/{id}/translations")
    public ResponseEntity<ProjectDto> getProjectByLanguage(@PathVariable Long id, @RequestHeader(value = "Accept-Language", defaultValue = "es") String language) {
        ProjectDto projectDto = projectService.getProjectByLanguage(id, language);
        return ResponseEntity.ok(projectDto);
    }

}
