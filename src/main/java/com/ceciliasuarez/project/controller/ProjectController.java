package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.model.Project;
import com.ceciliasuarez.project.service.IProjectService;
import com.ceciliasuarez.project.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final IProjectService projectService;

    @Autowired
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public List<Project> getAllProject(){
        return projectService.getAllProject();
    }

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }

    @PostMapping("/new")
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public Project updateProject(@PathVariable Long id, Project project){
        return projectService.updateProject(id, project);
    }
}
