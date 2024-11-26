package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.model.Project;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProjectService {
    Optional<Project> getProjectById(Long id);

    List<Project> getAllProject(); //Ordenar por año

    Project createProject(Project project);

    void updateProject(Project project);

    void deleteProject(Long id);

    /*List<String> uploadImages(List<MultipartFile> files) throws IOException;*/
}
