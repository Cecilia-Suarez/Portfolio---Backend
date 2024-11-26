package com.ceciliasuarez.project.controller;

import com.ceciliasuarez.project.model.Project;
import com.ceciliasuarez.project.service.IProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:5173/")
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

    @GetMapping("/detail/{id}")
    public Optional<Project> getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }

   /* @PostMapping("/new")
    public ResponseEntity<Project> createProject(@Valid @RequestParam("images") List<MultipartFile> images, @RequestBody Project project) throws IOException {
        List<String> imageUrls = projectService.uploadImages(images);
        project.setImages(imageUrls);
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);

    }*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project){
        projectService.updateProject(project);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
