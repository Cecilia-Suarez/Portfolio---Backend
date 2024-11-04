package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.model.Project;
import com.ceciliasuarez.project.repository.IProjectRepository;
import com.ceciliasuarez.project.service.IProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    private IProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private static final Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class);

    @Override
    public Optional<Project> getProjectById(Long id) {
       return projectRepository.findById(id);
    }

    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void updateProject(Project project) {
        if (getProjectById(project.getId()) !=null) {
            projectRepository.save(project);
        }
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
