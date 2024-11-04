package com.ceciliasuarez.project.repository;

import com.ceciliasuarez.project.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Long> {
}
