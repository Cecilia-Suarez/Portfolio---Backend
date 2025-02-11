package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.Dto.SkillDto;
import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Skill;
import com.ceciliasuarez.project.model.translation.SkillTranslation;
import com.ceciliasuarez.project.repository.ISkillRepository;
import com.ceciliasuarez.project.repository.translation.ISkillTranslationRepository;
import com.ceciliasuarez.project.service.ISkillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements ISkillService {

    private static final String DEFAULT_LANGUAGE = "es";
    private final ISkillRepository skillRepository;
    private final ISkillTranslationRepository translationRepository;

    private static final Logger logger = Logger.getLogger(SkillServiceImpl.class);

    @Autowired
    public SkillServiceImpl(ISkillRepository skillRepository, ISkillTranslationRepository translationRepository) {
        this.skillRepository = skillRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    public List<Skill> getAllSkill() {
        logger.info("Skill list:");
        return skillRepository.findAll();
    }

    @Override
    public Skill createSkill(Skill skill) throws DuplicateResourceException {
        logger.info("Saving new skill...");

        if (skill.getImage() == null || skill.getImage().trim().isEmpty()) {
            logger.warn("Error: La imagen no puede ser nula o vacía.");
            throw new IllegalArgumentException("La imagen no puede ser nula o vacía.");
        }

        for (SkillTranslation newTranslation : skill.getTranslations()) {
            boolean exists = translationRepository.existsByNameAndLanguageCode(
                    newTranslation.getName(), newTranslation.getLanguageCode()
            );
            if (exists) {
                logger.warn("Error: A skill with this name already exists in this language.");
                throw new DuplicateResourceException("A skill with this name already exists in this language.");
            }
        }

        for (SkillTranslation translation : skill.getTranslations()) {
            translation.setSkill(skill);
        }

        Skill savedSkill = skillRepository.save(skill);
        logger.info("Skill saved successfully!");
        return savedSkill;
    }

    @Override
    public void deleteSkill(Long id) {
        logger.info("Removing skill with id " + id);
        if (!skillRepository.findById(id).isPresent()) {
            logger.info("Skill removal failure.");
            throw new ResourceNotFoundException("There is no skill with the id " + id);
        }
        logger.info("Skill removed successfully!");
        skillRepository.deleteById(id);
    }

    @Override
    public SkillTranslation addTranslation(Long skillId, SkillTranslation translation) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));

        translation.setSkill(skill);
        return translationRepository.save(translation);
    }

    @Override
    public void updateSkill(Long skillId, Skill skillUpdates) {
        logger.info("Updating skill with id: " + skillId);

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId + " does not exist."));

        boolean isSkillUpdated = false;

        if (skillUpdates.getImage() != null && !skillUpdates.getImage().equals(skill.getImage())) {
            skill.setImage(skillUpdates.getImage());
            isSkillUpdated = true;
        }


        boolean isTranslationUpdated = false;

        for (SkillTranslation translation : skillUpdates.getTranslations()){
            SkillTranslation existingTranslation = translationRepository.findBySkillIdAndLanguageCode(skill.getId(), translation.getLanguageCode()).orElseThrow();

            if (existingTranslation.getName() != null && !existingTranslation.getName().equals(translation.getName())) {
                existingTranslation.setName(translation.getName());
                isTranslationUpdated = true;
            }

            if (isTranslationUpdated) {
                existingTranslation.setSkill(skill);
                translationRepository.save(existingTranslation);
            }
        }

        if (isSkillUpdated){
            skillRepository.save(skill);
        }

        logger.info("Skill and translation updated successfully!");
    }

    @Override
    public SkillDto getSkillByLanguage(Long skillId, String language) {
        logger.info("Searching skill by id and language...");

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));

        SkillTranslation translation = translationRepository
                .findBySkillIdAndLanguageCode(skillId, language)
                .or(() -> translationRepository.findBySkillIdAndLanguageCode(skillId, DEFAULT_LANGUAGE))
                .orElseThrow(() -> new ResourceNotFoundException("No translation found for skill id: " + skillId));

        logger.info("Skill found successfully.");

        return new SkillDto(skill.getId(), translation.getName(), skill.getImage());
    }

}
