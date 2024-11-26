package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Project;
import com.ceciliasuarez.project.repository.IProjectRepository;
import com.ceciliasuarez.project.service.IProjectService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    private IProjectRepository projectRepository;
    private final Cloudinary cloudinary;

    @Autowired
    public ProjectServiceImpl(IProjectRepository projectRepository, Cloudinary cloudinary) {
        this.projectRepository = projectRepository;
        this.cloudinary = cloudinary;
    }

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);

    @Override
    public Optional<Project> getProjectById(Long id) {
        logger.info("Searching project by id...");
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()){
            logger.info("Error when searching the project with the id " + id);
            throw new ResourceNotFoundException("The project with the id does not exist.");
        }
        logger.info("Project found successfully.");
        return project;
    }

    @Override
    public List<Project> getAllProject() {
        logger.info("Projects list:");
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project) {
        logger.info("Saving new project...");
        List<Project> listProjects = getAllProject();
        for (Project existingProject: listProjects) {
            if (existingProject.getName().equals(project.getName())){
                logger.info("Error when saving new project.");
                throw new DuplicateResourceException("A project with this name already exists.");
            }
            if (existingProject.getSite().equals(project.getSite())) {
                logger.info("Error when saving new project.");
                throw new DuplicateResourceException("A project with this site already exists.");
            }
            if (existingProject.getRepository().equals(project.getRepository())){
                logger.info("Error when saving new project.");
                throw new DuplicateResourceException("A project with this repository already exists.");
            }
        }
        logger.info("Project saved successfully!");
        return projectRepository.save(project);
    }

    @Override
    public void updateProject(Project project) {
        logger.info("Updating project...");
        if (getProjectById(project.getId()) == null){
            logger.info("Error updating project.");
            throw new ResourceNotFoundException("The project could not be updated, because there is no project with the given id.");
        }
        logger.info("Project updated successfully!");
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        logger.info("Removing project with id " + id);
        if (!projectRepository.findById(id).isPresent()) {
            logger.info("Project removal failure.");
            throw new ResourceNotFoundException("There is no project with the id " + id);
        }
        logger.info("Project removed successfully!");
        projectRepository.deleteById(id);
    }

    /*@Override
    public List<String> uploadImages(List<MultipartFile> files) throws IOException {
        logger.info("Uploading images to Cloudinary...");
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            imageUrls.add(imageUrl);
        }
        logger.info("Images uploaded successfully.");
        return imageUrls;
    }*/

}
