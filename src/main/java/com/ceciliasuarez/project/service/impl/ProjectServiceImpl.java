package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.Dto.ProjectDto;
import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Project;
import com.ceciliasuarez.project.model.Skill;
import com.ceciliasuarez.project.model.translation.ProjectTranslation;
import com.ceciliasuarez.project.repository.ICategoryRepository;
import com.ceciliasuarez.project.repository.IProjectRepository;
import com.ceciliasuarez.project.repository.ISkillRepository;
import com.ceciliasuarez.project.repository.translation.IProjectTranslationRepository;
import com.ceciliasuarez.project.service.IProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    public static final String DEFAULT_LANGUAGE = "es";
    private final IProjectRepository projectRepository;
    private final IProjectTranslationRepository translationRepository;
    private final ICategoryRepository categoryRepository;
    private final ISkillRepository skillsRepository;

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);

    @Autowired
    public ProjectServiceImpl(IProjectRepository projectRepository, IProjectTranslationRepository translationRepository, ISkillRepository skillsRepository, ICategoryRepository categoryRepository) {
        this.projectRepository = projectRepository;
        this.translationRepository = translationRepository;
        this.categoryRepository = categoryRepository;
        this.skillsRepository = skillsRepository;
    }

    @Override
    public List<Project> getAllProject() {
        logger.info("Fetching all projects...");
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        logger.info("Searching project by id...");
        return projectRepository.findById(id).orElseThrow(() -> {
            logger.info("Error when searching the project with the id " + id);
            return new ResourceNotFoundException("The project with the id does not exist.");
        });
    }

    @Override
    public Project createProject(Project project) {
        logger.info("Saving new project...");

        if (translationExists(project.getTranslations())) {
            logger.warn("Error: A project with this name already exists for the given language.");
            throw new DuplicateResourceException("A project with this name already exists for the given language.");
        }

        if (projectRepository.existsBySite(project.getSite())) {
            logger.warn("Error: A project with this site already exists.");
            throw new DuplicateResourceException("A project with this site already exists.");
        }

        if (projectRepository.existsByRepository(project.getRepository())) {
            logger.warn("Error: A project with this repository already exists.");
            throw new DuplicateResourceException("A project with this repository already exists.");
        }

        categoryRepository.findById(project.getCategory().getId()).orElseThrow(() -> {
            logger.info("Category removal failure.");
            return new ResourceNotFoundException("There is no category with the id " + project.getCategory().getId());
        });

        for (Skill skill : project.getSkills()) {
            skillsRepository.findById(skill.getId()).orElseThrow(() -> {
                logger.info("Skill removal failure.");
                return new ResourceNotFoundException("There is no Skill with the id " + skill.getId());
            });
        }

        for (ProjectTranslation translation : project.getTranslations()) {
            translation.setProject(project);
        }

        logger.info("Project saved successfully!");
        return projectRepository.save(project);
    }

    @Override
    public ProjectTranslation addTranslation(Long projectId, ProjectTranslation translation) {
        logger.info("Adding translation for project id " + projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    logger.info("Error adding translation: project not found.");
                    return new ResourceNotFoundException("Project not found with id: " + projectId);
                });

        translation.setProject(project);
        return translationRepository.save(translation);
    }

    @Override
    public ProjectDto getProjectByLanguage(Long projectId, String language) {
        logger.info("Searching project by id " + projectId + " and language " + language);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        ProjectTranslation translation = translationRepository
                .findByProjectIdAndLanguageCode(project.getId(), language)
                .or(() -> translationRepository.findByProjectIdAndLanguageCode(project.getId(), DEFAULT_LANGUAGE))
                .orElseThrow(() -> new ResourceNotFoundException("No translation found for project id: " + projectId));

        logger.info("Project found successfully.");

        return new ProjectDto(
                project.getId(),
                translation.getName(),
                translation.getDescription(),
                project.getYear(),
                project.getSite(),
                project.getRepository(),
                project.getCategory(),
                project.getSkills(),
                project.getImages()
        );
    }

    @Override
    public void updateProject(Long projectId, Project projectUpdates) {
        logger.info("Updating project with id: " + projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("The project with id " + projectId + " does not exist."));

        boolean isProjectUpdated = false;

        if (projectUpdates.getYear() != 0 && projectUpdates.getYear() != project.getYear()) {
            project.setYear(projectUpdates.getYear());
            isProjectUpdated = true;
        }
        if (projectUpdates.getSite() != null && !projectUpdates.getSite().equals(project.getSite())) {
            project.setSite(projectUpdates.getSite());
            isProjectUpdated = true;
        }
        if (projectUpdates.getRepository() != null && !projectUpdates.getRepository().equals(project.getRepository())) {
            project.setRepository(projectUpdates.getRepository());
            isProjectUpdated = true;
        }
        if (projectUpdates.getCategory() != null && !projectUpdates.getCategory().equals(project.getCategory())) {
            project.setCategory(projectUpdates.getCategory());
            isProjectUpdated = true;
        }
        if (projectUpdates.getSkills() != null && !projectUpdates.getSkills().equals(project.getSkills())) {
            project.setSkills(projectUpdates.getSkills());
            isProjectUpdated = true;
        }
        if (projectUpdates.getImages() != null && !projectUpdates.getImages().equals(project.getImages())) {
            project.setImages(projectUpdates.getImages());
            isProjectUpdated = true;
        }
        boolean isTranslationUpdated = false;

        for (ProjectTranslation translation : projectUpdates.getTranslations()) {
            ProjectTranslation existingTranslation = translationRepository.findByProjectIdAndLanguageCode(project.getId(), translation.getLanguageCode()).orElseThrow();

            if (translation.getName() != null && !translation.getName().equals(existingTranslation.getName())) {
                existingTranslation.setName(translation.getName());
                isTranslationUpdated = true;
            }
            if (translation.getDescription() != null && !translation.getDescription().equals(existingTranslation.getDescription())) {
                existingTranslation.setDescription(translation.getDescription());
                isTranslationUpdated = true;
            }
            System.out.println(isTranslationUpdated);
            if (isTranslationUpdated) {
                existingTranslation.setProject(project);
                translationRepository.save(existingTranslation);
            }
        }

        if (isProjectUpdated) {
            projectRepository.save(project);
        }

        logger.info("Project and translation updated successfully!");
    }

    @Override
    public void deleteProject(Long id) {
        logger.info("Removing project with id: " + id);

        if (!projectRepository.existsById(id)) {
            logger.warn("Project removal failure: No project found with id " + id);
            throw new ResourceNotFoundException("There is no project with the id " + id);
        }

        logger.info("Project removed successfully!");
        projectRepository.deleteById(id);
    }

    private boolean translationExists(List<ProjectTranslation> translations) {
        for (ProjectTranslation translation : translations) {
            if (translationRepository.existsByNameIgnoreCase(translation.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

