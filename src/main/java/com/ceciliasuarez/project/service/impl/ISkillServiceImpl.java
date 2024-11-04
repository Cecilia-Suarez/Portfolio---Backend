package com.ceciliasuarez.project.service.impl;

import com.ceciliasuarez.project.model.Skill;
import com.ceciliasuarez.project.repository.ISkillRepository;
import com.ceciliasuarez.project.service.ISkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISkillServiceImpl implements ISkillService {
    @Autowired
    private ISkillRepository skillRepository;

    @Override
    public List<Skill> getAllSkill() {
        return skillRepository.findAll();
    }

    @Override
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}
