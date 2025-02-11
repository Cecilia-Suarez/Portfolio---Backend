package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.Dto.ProjectDto;
import com.ceciliasuarez.project.model.Project;
import com.ceciliasuarez.project.model.translation.ProjectTranslation;

import java.util.List;

public interface IProjectService {

    List<Project> getAllProject(); //Ordenar por año

    Project getProjectById(Long id);

    Project createProject(Project project);

    void updateProject(Long projectId, Project projectUpdates);

    void deleteProject(Long id);

    ProjectTranslation addTranslation(Long projectId, ProjectTranslation translation);

    ProjectDto getProjectByLanguage(Long projectId, String language);

}
