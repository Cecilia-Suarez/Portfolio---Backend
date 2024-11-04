package com.ceciliasuarez.project.service;

import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.model.Skill;
import java.util.List;

public interface ISkillService {
    List<Skill> getAllSkill();

    Skill createSkill(Skill skill);

    void deleteSkill(Long id);
}
