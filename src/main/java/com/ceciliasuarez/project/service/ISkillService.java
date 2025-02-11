package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.Dto.SkillDto;
import com.ceciliasuarez.project.exceptions.DuplicateResourceException;
import com.ceciliasuarez.project.model.Skill;
import com.ceciliasuarez.project.model.translation.SkillTranslation;

import java.util.List;

public interface ISkillService {
    List<Skill> getAllSkill();

    Skill createSkill(Skill skill) throws DuplicateResourceException;

    void deleteSkill(Long id);

    SkillTranslation addTranslation(Long skillId, SkillTranslation translation);

    void updateSkill(Long skillId, Skill skillUpdates);

    SkillDto getSkillByLanguage(Long skillId, String language);
}
