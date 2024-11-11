package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.exceptions.ResourceNotFoundException;
import com.ceciliasuarez.project.model.Skill;
import com.ceciliasuarez.project.repository.ISkillRepository;
import com.ceciliasuarez.project.service.ISkillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements ISkillService {
    @Autowired
    private ISkillRepository skillRepository;

    private static final Logger logger = Logger.getLogger(SkillServiceImpl.class);

    @Override
    public List<Skill> getAllSkill() {
        logger.info("Skill list:");
        return skillRepository.findAll();
    }

    @Override
    public Skill createSkill(Skill skill) {
        logger.info("Saving new skill...");
        List<Skill> listSkills = getAllSkill();
        for (Skill existingSkill: listSkills) {
            if (existingSkill.getName().equals(skill.getName())){
                logger.info("Error when saving new skill.");
                throw new DuplicateResourceException("There is already a skill with that name.");
            }
        }
        logger.info("Skill saved successfully!");
        return skillRepository.save(skill);
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

}
